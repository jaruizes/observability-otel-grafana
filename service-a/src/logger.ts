import {createLogger, transports, format, level} from 'winston';
import opentelemetry from "@opentelemetry/api";
import {TransformableInfo} from "logform";


// @ts-ignore
const traceGenerator = (traceInfo: TransformableInfo) => {
    let current_span = opentelemetry.trace.getSpan(opentelemetry.context.active());
    let trace_id = current_span ? current_span.spanContext().traceId : '';
    let span_id = current_span ? current_span.spanContext().spanId: '';

    return `[${traceInfo.timestamp}] level=${traceInfo.level} , traceId=${trace_id} , spanId=${span_id} : ${traceInfo.message}`;
}

export const logger = createLogger({
    transports: [new transports.Console()],
    level: level,
    format: format.combine(
        format.colorize(),
        format.timestamp(),
        format.printf(traceGenerator)
    ),
});
