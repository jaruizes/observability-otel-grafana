import express, { Express } from "express";
import axios from 'axios';
import { Kafka, KafkaConfig, EachMessagePayload, logLevel, LogEntry } from 'kafkajs';
import { logger } from "./logger";
import {collectDefaultMetrics, register} from "prom-client";
import promBundle from 'express-prom-bundle';

const metricsMiddleware = promBundle({
    includeMethod: true,
    includePath: true,
    includeStatusCode: true,
    includeUp: true,
    customLabels: {project_name: 'service-a', project_type: 'metrics'},
    promClient: {
        collectDefaultMetrics: {
        }
    }
});

const kafkaBrokers = process.env.KAFKA_BOOTSTRAP_SERVERS || 'localhost:29092';
const serviceBURL = process.env.SERVICEB_URL || 'http://localhost:8081';

const toWinstonLogLevel = (level: logLevel): string => {
    switch (level) {
        case logLevel.ERROR:
            return 'error'
        case logLevel.WARN:
            return 'warn'
        case logLevel.INFO:
            return 'info'
        case logLevel.DEBUG:
        default:
            return 'debug'
    }
}

const kafkaLogCreator = (logLevel: logLevel) => {
        return (logEntry: LogEntry) => {
            const { message, ...extra } = logEntry.log
            logger.log({
                level: toWinstonLogLevel(logLevel),
                message,
                extra,
            })
    }
}

const kafkaConfig: KafkaConfig = {
    brokers: [ kafkaBrokers ],
    connectionTimeout: 10000,
    requestTimeout: 60000,
    retry: { initialRetryTime: 45000, retries: 20},
    logCreator: kafkaLogCreator,
    logLevel: logLevel.ERROR
};

const kafka = new Kafka(kafkaConfig);

const consumer = kafka.consumer({ groupId: 'service-a' })


consumer.connect()
consumer.subscribe({ topic: 'topic-d', fromBeginning: true })
consumer.run({
    eachMessage: async ({ topic, partition, message }: EachMessagePayload) => {
        console.log({
            value: message.value?.toString(),
        })
    },
})

const PORT: number = parseInt(process.env.PORT || "8080");
const index: Express = express();

index.use(express.json());
index.use(express.urlencoded({ extended: true }));
index.use(metricsMiddleware);

index.get('/metrics/prometheus', async (_req, res) => {
    try {
        res.set('Content-Type', register.contentType);
        res.end(await register.metrics());
    } catch (err) {
        res.status(500).end(err);
    }
});


index.post("/", (req, res) => {
    const processId = Date.now();
    const initialValue = req.body.initialValue
    callServiceB(processId, initialValue).then(r => res.send({'initialValue': initialValue}));

});

index.listen(PORT, () => {
    logger.info(`Listening for requests on http://localhost:${PORT}`);
});


async function callServiceB(processId: number, initialValue: number) {
    try {
        logger.info(`Calling service b`);
        const response = await axios.post(`${serviceBURL}/b-service`, {
            "id": processId,
            "initialValue": initialValue,
            "currentValue": initialValue
        });
        logger.info(`Response received ${response.status}`);
    } catch (error) {
        logger.error(error);
    }
}
