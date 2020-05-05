package nl.fhict.s4;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.mutiny.core.Vertx;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import nl.fhict.s4.models.EventModel;

import org.jboss.resteasy.annotations.Body;
import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.annotations.cache.Cache;
import org.reactivestreams.Publisher;

import javax.enterprise.inject.Model;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Path("/events")
public class EventResource {

	Publisher<EventModel> events;
	Multi<EventModel> cachedEvents;
	Emitter<EventModel> eventEmitter;
	Vertx vertx;


	List<EventModel> models = new ArrayList<EventModel>();


	public EventResource(
			@Channel("event-create") Emitter<EventModel> eventEmitter,
			@Channel("events") Publisher<EventModel> events,
			@Context Vertx vertx
	) {
		this.events = events;
		this.eventEmitter = eventEmitter;
		this.vertx = vertx;

		cachedEvents = Multi
				.createFrom()
				.publisher(events)
				.cache();


		models.add(new EventModel(11, 0, 37, "LeonIsTesting"));
	}


	@GET
	@Path("/stream/{type}")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@SseElementType(MediaType.APPLICATION_JSON)
	public Multi<EventModel> events(@PathParam("type") int type) {

		return cachedEvents
				.transform()
				.byFilteringItemsWith(e -> e.getType() == type);
	}


	@GET
	@Produces(MediaType.TEXT_HTML)
	@Cache(noStore = false, isPrivate = false, maxAge = 31_536_000)
	public Uni<String> eventsPage() {
		return vertx
				.fileSystem()
				.readFile("/META-INF/resources/stream.html")
				.onItem()
				.apply(buffer -> buffer.toString("UTF-8"));
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public EventModel addEvent(@Form EventModel model) {
		eventEmitter.send(model);
		return model;
	}

	@GET
	@Path("/testing")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventModel> testingFunctie() {
		return models;
	}

	@POST
	@Path("/testing")
	@Consumes(MediaType.APPLICATION_JSON)
	public String testingFunctie2(@Form EventModel model) {
		// currently this method doesn't work,
		// as it is apparently not possible for the parameter data to be interpreted as an EventModel
		try{
			models.add(model);
			return "We did it!";
		}
		catch (Exception e){
			return "Mission Failed!";
		}
	}
}
