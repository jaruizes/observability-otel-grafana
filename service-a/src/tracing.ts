import * as opentelemetry from '@opentelemetry/sdk-node';
import { OTLPTraceExporter } from "@opentelemetry/exporter-trace-otlp-grpc";
import { Resource } from "@opentelemetry/resources";
import { SemanticResourceAttributes } from "@opentelemetry/semantic-conventions";
import { HttpInstrumentation } from "@opentelemetry/instrumentation-http";
import { ExpressInstrumentation, ExpressLayerType } from "@opentelemetry/instrumentation-express";
import { KafkaJsInstrumentation } from "opentelemetry-instrumentation-kafkajs";
import { logger } from "./logger";
import { WinstonInstrumentation } from '@opentelemetry/instrumentation-winston';


const otelTracerExporterEndpoint = process.env.OPENTELEMETRY_TRACER_EXPORTER_OTLP_ENDPOINT || 'http://localhost:4317';
const sdk = new opentelemetry.NodeSDK({
    traceExporter: new OTLPTraceExporter({
        url: otelTracerExporterEndpoint,
        headers: {},
    }),
    instrumentations: [
        new HttpInstrumentation(),
        new ExpressInstrumentation({ignoreLayersType: [ExpressLayerType.MIDDLEWARE]}),
        new KafkaJsInstrumentation(),
        new WinstonInstrumentation()
    ],
    resource: new Resource({
        [SemanticResourceAttributes.SERVICE_NAME]: "service-a (Node)",
        [SemanticResourceAttributes.SERVICE_VERSION]: "0.1.0",
    })
});

sdk.start().then(r => logger.info("Tracer started"));
