package nl.fhict.s4;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import io.quarkus.scheduler.Scheduled;
import io.reactivex.Flowable;
import io.smallrye.mutiny.Multi;
import nl.fhict.s4.models.EventModel;
import nl.fhict.s4.models.EventType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;



@ApplicationScoped
public class EventGenerator {

    private static final Logger LOG = Logger.getLogger(EventGenerator.class);
    private final Random random = new Random();
    private List<EventType> types;

    @Inject UserTransaction transaction;

    @Scheduled(every="60s")     
    public void refreshEventTypes() {
        //updates the list of types the generator can use
        types = EventType.listAll();
    }

   @Outgoing("generated-event")
    public Publisher<EventModel> generate() {
        types = EventType.listAll();
        
        return Multi.
            createFrom()
            .ticks()
            .every(Duration.ofSeconds(6))
            .flatMap(t -> createEvent())
            .onOverflow()
            .drop();      
    }

    private Multi<EventModel> createEvent() {
        //if there are no types in the database it is not possible to generate an event.
        if(types.size() == 0) {
            return Multi.createFrom().empty();  
        }

        //tries to create a transaction. 
        //If it succeeded an event will be created and saved, the method will return an empty Multi object
        try {
            transaction.begin();

            double lat = 51.2 + random.nextDouble() * (51.52 - 51.2);
            double lon = 5.18 + random.nextDouble() * (5.82 - 5.18);
            EventType type = types.get(random.nextInt(types.size()));

            EventModel model = new EventModel(lat, lon, type, "description");
          
            model.persist();
            transaction.commit();
          
        }
        catch(Exception e) {
            LOG.error(e.getStackTrace());
            return Multi.createFrom().empty();  
        }
    }

   @Outgoing("generated-event")
    public Publisher<EventModel> generate() {

       return Multi.
            createFrom()
            .ticks()
            .every(Duration.ofSeconds(1))
            .map(t -> createEvent())
            .transform()
            .byFilteringItemsWith(m -> m != null);
    }

    private EventModel createEvent() {
        //tries to create a transaction. 
        //If it succeeded an event will be created and saved, if it didn't the method will return null.
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