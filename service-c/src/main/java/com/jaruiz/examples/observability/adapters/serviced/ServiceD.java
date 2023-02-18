package com.jaruiz.examples.observability.adapters.serviced;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.jaruiz.examples.observability.adapters.serviced.client.ServiceDClient;
import com.jaruiz.examples.observability.adapters.serviced.client.dto.ProcessDataDTO;
import com.jaruiz.examples.observability.business.CService;
import com.jaruiz.examples.observability.business.model.ProcessData;
import com.jaruiz.examples.observability.business.ports.ServiceDPort;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ServiceD implements ServiceDPort {

    private static final Logger LOG = Logger.getLogger(ServiceD.class);

    @Inject
    @RestClient
    ServiceDClient serviceDClient;

    @Override public ProcessData callService(ProcessData processData) {
        LOG.info("Calling service D. Id: " + processData.getId() + " / Init value: " + processData.getInitValue() + " / Current value: " + processData.getCurrentValue());
        final Response response = serviceDClient.updateProcess(bm2DTO(processData));
        var processDataResponse = response.readEntity(ProcessDataDTO.class);

        LOG.info("Response received. Id: " + processDataResponse.getId() + " / Init value: " + processDataResponse.getInitialValue() + " / Current value: " + processDataResponse.getCurrentValue());

        return dto2BM(processDataResponse);
    }

    private ProcessDataDTO bm2DTO(ProcessData processData) {
        return new ProcessDataDTO(processData.getId(), processData.getInitValue(), processData.getCurrentValue());
    }

    private ProcessData dto2BM(ProcessDataDTO processDataDTO) {
        return new ProcessData(processDataDTO.getId(), processDataDTO.getInitialValue(), processDataDTO.getCurrentValue());
    }
}
