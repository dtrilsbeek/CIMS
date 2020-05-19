package nl.fhict.s4;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import nl.fhict.s4.models.EventModel;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Outgoing;


@ApplicationScoped
public class EventGenerator {
    private final Random random = new Random();

    @Transactional
    @Outgoing("generated-event")
    public Flowable<EventModel> generate() {

        return Flowable.interval(10, TimeUnit.SECONDS)
                .map(tick -> createEvent() );
    }

    private EventModel createEvent(){

        EventModel model = new EventModel(
                51.2 + random.nextDouble() * (51.52 - 51.2),
                5.18 + random.nextDouble() * (5.82 - 5.18),
                random.nextInt(7) + 1,
                "description");

        model.persist();

        return model;
    }

}