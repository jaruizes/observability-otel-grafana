package com.jaruiz.examples.observability.business;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.jaruiz.examples.observability.business.model.ProcessData;
import com.jaruiz.examples.observability.business.ports.CServiceBusinessPort;
import com.jaruiz.examples.observability.business.ports.ServiceDPort;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CService implements CServiceBusinessPort {
    private static final Logger LOG = Logger.getLogger(CService.class);

    @Inject ServiceDPort serviceD;
    public void updateProcess(ProcessData processData) {
        if (processData.getCurrentValue() >= 0 && processData.getCurrentValue() < 15) {
            throw new RuntimeException("Esto es un fallo deliberado del servicio C");
        }

        final ProcessData processDataUpdated = new ProcessData(processData.getId(), processData.getInitValue(), processData.getCurrentValue() + 10);

        LOG.info("Updated values. Id: " + processDataUpdated.getId() + " / Init value: " + processDataUpdated.getInitValue() + " / Current value: " + processDataUpdated.getCurrentValue());
        serviceD.callService(processDataUpdated);
    }
}
