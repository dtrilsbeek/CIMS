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

    @Outgoing("generated-event")
    @Transactional
    public Flowable<EventModel> generate() {


        return Flowable.interval(10, TimeUnit.SECONDS)
                .map(tick -> {
                    var event = new EventModel(
                                    random.nextDouble() * 50,
                                    random.nextDouble() * 5,
                                    random.nextInt(7) + 1,
                                    "description");
                    event.persist();
                    return event;

                        }
                );
    }

}