package com.jaruiz.examples.observability.bservicespring.business.ports;

import com.jaruiz.examples.observability.bservicespring.business.model.ProcessData;

public interface BServiceBusinessPort {
    ProcessData updateProcess(ProcessData processData);
}
