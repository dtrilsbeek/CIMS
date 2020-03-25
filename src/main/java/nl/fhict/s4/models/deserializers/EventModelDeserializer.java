package nl.fhict.s4.models.deserializers;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import nl.fhict.s4.models.EventModel;

public class EventModelDeserializer extends ObjectMapperDeserializer<EventModel> {
    public EventModelDeserializer() {
        super(EventModel.class);
    }
}