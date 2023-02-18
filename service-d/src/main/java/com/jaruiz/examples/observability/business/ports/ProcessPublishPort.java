package com.jaruiz.examples.observability.business.ports;

import com.jaruiz.examples.observability.business.model.ProcessData;

public interface ProcessPublishPort {
    void publish(ProcessData processData);
}
