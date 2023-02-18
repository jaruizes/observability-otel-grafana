package com.jaruiz.examples.observability.business.ports;

import com.jaruiz.examples.observability.business.model.ProcessData;

public interface ServiceDPort {
    ProcessData callService(ProcessData processData);
}
