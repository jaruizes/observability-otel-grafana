package com.jaruiz.examples.observability.adapters.serviced.client;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.jaruiz.examples.observability.adapters.serviced.client.dto.ProcessDataDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/d-service")
@RegisterRestClient(configKey="serviced-api")
public interface ServiceDClient {

    @POST Response updateProcess(final ProcessDataDTO processDataDTO);
}
