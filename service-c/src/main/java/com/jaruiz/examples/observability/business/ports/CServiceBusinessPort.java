package com.jaruiz.examples.observability.business.ports;

import com.jaruiz.examples.observability.business.model.ProcessData;

public interface CServiceBusinessPort {
    void updateProcess(ProcessData processData);
}
