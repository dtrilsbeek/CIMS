package nl.fhict.s4;


import io.smallrye.reactive.messaging.annotations.Channel;
import nl.fhict.s4.models.EventModel;

import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.annotations.cache.Cache;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.InputStream;

@Path("/events")
public class EventResource {
    
    @Inject
    @Channel("events") Publisher<EventModel> events; 

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Publisher<EventModel> streamEvents() {
       return events;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Cache(noStore = false, isPrivate = false, maxAge = 31_536_000)
    public InputStream eventsPage() {
        return this
            .getClass()
            .getResourceAsStream("/META-INF/resources/stream.html");
    }
}
