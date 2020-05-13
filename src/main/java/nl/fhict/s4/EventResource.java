package nl.fhict.s4;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import nl.fhict.s4.models.EventModel;


import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.annotations.cache.Cache;
import org.reactivestreams.Publisher;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;



@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Path("/events")
public class EventResource {

	Publisher<EventModel> events;
	Multi<EventModel> cachedEvents;
	Emitter<EventModel> eventEmitter;
	Vertx vertx;


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
	}


	@GET
	@Path("/stream")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@SseElementType(MediaType.APPLICATION_JSON)
	public Multi<EventModel> events() {
		return cachedEvents;
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
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EventModel createEvent(EventModel model) {
		model = new EventModel(model);
		model.persist();

		eventEmitter.send(model);

		return model;
	}

	@PUT
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EventModel updateEvent(EventModel model) {
		EventModel update = EventModel.findById(model.id);
		update.status = model.status;
		update.description = model.description;
		update.lat = model.lat;
		update.lon = model.lon;
		update.type = model.type;
		update.persist();

		eventEmitter.send(model);

		return model;
	}
}