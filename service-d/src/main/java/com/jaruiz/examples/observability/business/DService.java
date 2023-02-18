package com.jaruiz.examples.observability.business;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.jaruiz.examples.observability.business.model.ProcessData;
import com.jaruiz.examples.observability.business.ports.DServiceBusinessPort;
import com.jaruiz.examples.observability.business.ports.ProcessPublishPort;
import org.jboss.logging.Logger;

@ApplicationScoped
public class DService implements DServiceBusinessPort {

    private static final Logger LOG = Logger.getLogger(DService.class);

    @Inject
    private ProcessPublishPort publishService;

    public ProcessData updateProcess(ProcessData processData) {
        if (processData.getCurrentValue() >= 30 && processData.getCurrentValue() < 45) {
            throw new RuntimeException("Esto es un fallo deliberado del servicio D");
        }

        final ProcessData processDataUpdated = new ProcessData(processData.getId(), processData.getInitValue(), processData.getCurrentValue() + 10);
        LOG.info("Updated values. Id: " + processDataUpdated.getId() + " / Init value: " + processDataUpdated.getInitValue() + " / Current value: " + processDataUpdated.getCurrentValue());

        publishService.publish(processDataUpdated);

        return processDataUpdated;
    }
}
