package nl.fhict.s4;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import nl.fhict.s4.models.EventModel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;


@ApplicationScoped
public class EventConverter {


/*    @Incoming("events")
    @Outgoing("converted-event-stream")
    @Transactional
    @Broadcast
    public EventModel process(EventModel model) {

        model.persist();

        return model;
    }*/

}