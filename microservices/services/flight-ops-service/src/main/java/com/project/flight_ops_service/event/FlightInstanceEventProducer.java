package com.project.flight_ops_service.event;



import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.project.event.FlightInstanceCreatedEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class FlightInstanceEventProducer {

    private final KafkaTemplate<String, FlightInstanceCreatedEvent> kafkaTemplate;

    public void sendFlightInstanceCreated(FlightInstanceCreatedEvent event) {

    System.out.println(
        "SENDING KAFKA EVENT: "
        + event.getFlightId()
        + "-"
        + event.getFlightInstanceId()
    );

    kafkaTemplate
        .send("flight-instance-created", event)
        .whenComplete((result, ex) -> {

            if (ex != null) {

                System.out.println("❌ KAFKA SEND FAILED");

                ex.printStackTrace();

            } else {

                System.out.println("✅ KAFKA SEND SUCCESS");

                System.out.println(
                    "Topic: "
                    + result.getRecordMetadata().topic()
                );

                System.out.println(
                    "Partition: "
                    + result.getRecordMetadata().partition()
                );

                System.out.println(
                    "Offset: "
                    + result.getRecordMetadata().offset()
                );
            }
        });
}

}
