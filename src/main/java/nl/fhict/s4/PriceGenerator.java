package nl.fhict.s4;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;


@ApplicationScoped
public class PriceGenerator {
    private final Random random = new Random();

    @Outgoing("generated-price")                        
    public Flowable<Integer> generate() {               
        return Flowable.interval(5, TimeUnit.SECONDS)
                .map(tick -> random.nextInt(100));
    }

}