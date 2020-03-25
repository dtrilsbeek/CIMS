package nl.fhict.s4;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import nl.fhict.s4.models.EventModel;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;


@ApplicationScoped
public class EventGenerator {
    private final Random random = new Random();

    @Outgoing("generated-event")                        
    public Flowable<EventModel> generate() {               
        return Flowable.interval(5, TimeUnit.SECONDS)
                .map(tick -> new EventModel( random.nextDouble() * 50, random.nextDouble() * 5, 1, "description"));
    }

}