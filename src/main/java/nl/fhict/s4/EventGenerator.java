package nl.fhict.s4;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import io.smallrye.mutiny.Multi;
import nl.fhict.s4.models.EventModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;
import org.reactivestreams.Publisher;



@ApplicationScoped
public class EventGenerator {

    private static final Logger LOG = Logger.getLogger(EventGenerator.class);
    private final Random random = new Random();
    @Inject UserTransaction transaction;
    
   @Outgoing("generated-event")
    public Publisher<EventModel> generate() {

       return Multi.
            createFrom()
            .ticks()
            .every(Duration.ofSeconds(6))
            .flatMap(t -> createEvent())
            .onOverflow()
            .drop();      
    }

    private Multi<EventModel> createEvent() {
        //tries to create a transaction. 
        //If it succeeded an event will be created and saved, the method will return an empty Multi object
        try {
            transaction.begin();
            EventModel model = new EventModel(
                    51.2 + random.nextDouble() * (51.52 - 51.2),
                    5.18 + random.nextDouble() * (5.82 - 5.18),
                    random.nextInt(7) + 1,
                    "description");

            model.persist();
            transaction.commit();

            return Multi.createFrom().item(model);
        }
        catch(Exception e) {
            LOG.error(e.getStackTrace());
            
            return Multi.createFrom().empty();  
        }
    }

}