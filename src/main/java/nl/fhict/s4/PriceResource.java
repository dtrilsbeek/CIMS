package nl.fhict.s4;

import io.reactivex.Flowable;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Path("/prices")
public class PriceResource {
    private Random random = new Random();


    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("text/plain")
    public Publisher<Integer> streamNumbers() {
        return Flowable.interval(200, TimeUnit.MILLISECONDS)
                .map(tick -> random.nextInt(99) + 1);

    }
}
