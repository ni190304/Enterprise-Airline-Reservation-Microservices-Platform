package com.project.flight_ops_service.event;



import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.project.event.FlightInstanceCreatedEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class FlightInstanceEventProducer {

    private final KafkaTemplate<String, FlightInstanceCreatedEvent> kafkaTemplate;

    public void sendFlightInstanceCreated(FlightInstanceCreatedEvent event){
        kafkaTemplate.send("flight-instance-created",event);
    }

}
