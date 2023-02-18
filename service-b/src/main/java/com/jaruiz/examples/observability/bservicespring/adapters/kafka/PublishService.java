package com.jaruiz.examples.observability.bservicespring.adapters.kafka;

import com.jaruiz.examples.observability.bservicespring.adapters.kafka.dto.ProcessDataMessage;
import com.jaruiz.examples.observability.bservicespring.business.model.ProcessData;
import com.jaruiz.examples.observability.bservicespring.business.ports.ProcessPublishPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublishService implements ProcessPublishPort {

    @Value("${service-b.topic}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, ProcessDataMessage> kafkaTemplate;

    @Override public void publish(ProcessData processData) {
        kafkaTemplate.setObservationEnabled(true);
        kafkaTemplate.send(topicName, bm2Message(processData));
    }

    private ProcessDataMessage bm2Message(ProcessData processData) {
        return new ProcessDataMessage(processData.getId(), processData.getInitValue(), processData.getCurrentValue());
    }
}
