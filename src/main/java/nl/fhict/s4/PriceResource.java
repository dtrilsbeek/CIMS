package nl.fhict.s4;


import io.smallrye.reactive.messaging.annotations.Channel;

import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.annotations.cache.Cache;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.InputStream;

@Path("/prices")
public class PriceResource {
    
    @Inject
    @Channel("my-data-stream") Publisher<Double> prices; 

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.TEXT_PLAIN)
    public Publisher<Double> streamNumbers() {
       return prices;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Cache(noStore = false, isPrivate = false, maxAge = 31_536_000)
    public InputStream pricePage() {
        return this
            .getClass()
            .getResourceAsStream("/META-INF/resources/stream.html");
    }
}
