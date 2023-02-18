package com.jaruiz.examples.observability.api.async;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.jaruiz.examples.observability.api.async.dto.ProcessDataMessage;
import com.jaruiz.examples.observability.business.model.ProcessData;
import com.jaruiz.examples.observability.business.ports.CServiceBusinessPort;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProcessConsumer {
    private static final Logger LOG = Logger.getLogger(ProcessConsumer.class);

    @Inject CServiceBusinessPort service;

    @Incoming("topic-b")
    @Blocking
    public void consume(ProcessDataMessage processDataMessage) {
        LOG.info("Consuming message from topic-b. Id: " + processDataMessage.getId() + " / Init value: " + processDataMessage.getInitValue() + " / Current value: " + processDataMessage.getCurrentValue());
        service.updateProcess(messageToBM(processDataMessage));
    }

    private ProcessData messageToBM(ProcessDataMessage processDataMessage) {
        return new ProcessData(processDataMessage.getId(), processDataMessage.getInitValue(), processDataMessage.getCurrentValue());
    }
}
