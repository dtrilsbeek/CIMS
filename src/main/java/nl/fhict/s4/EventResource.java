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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;



@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Path("/events")
public class EventResource {

	Publisher<EventModel> events;
	Multi<EventModel> eventBroadcast;
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

		eventBroadcast = Multi
				.createFrom()
					.publisher(events)
				.broadcast()
					.toAllSubscribers();
	}


	Multi<EventModel> addTypeFilter(MultivaluedMap<String, String> params, Multi<EventModel> stream) {
		try {
			Integer type = Integer.parseInt(params.getFirst("type"));
			return stream.transform().byFilteringItemsWith(e -> e.type == type);
		}
		catch (Exception e) {
			return stream;
		}	
	}

	Multi<EventModel> addRegionFilter(MultivaluedMap<String, String> params, Multi<EventModel> stream) {
		try {
			double sx = Double.parseDouble(params.getFirst("sx"));
			double sy = Double.parseDouble(params.getFirst("sy"));
			double ex = Double.parseDouble(params.getFirst("ex"));
			double ey = Double.parseDouble(params.getFirst("ey"));

			return stream
				.transform()
				.byFilteringItemsWith(e -> 
					e.getLat() > sx &&
					e.getLat() > sy &&
					e.getLon() < ex &&
					e.getLon() < ey
				);
		}
		catch (Exception e) {
			return stream;
		}
	}


	@GET
	@Path("/stream")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@SseElementType(MediaType.APPLICATION_JSON)
	public Multi<EventModel> events(@Context UriInfo ui) {
		var queryParams = ui.getQueryParameters();
		var stream = eventBroadcast;

		stream = addTypeFilter(queryParams, stream);
		stream = addRegionFilter(queryParams, stream);

		return stream;
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

		if (update != null) {
			update.isUpdate = true;
			update.status = model.status;
			update.description = model.description;
			update.lat = model.lat;
			update.lon = model.lon;
			update.type = model.type;
			update.persist();

			eventEmitter.send(update);
		}

		return model;
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
}