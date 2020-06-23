package nl.fhict.s4.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import nl.fhict.s4.models.EventModel;
import nl.fhict.s4.models.EventStatus;
import nl.fhict.s4.models.EventType;


import org.reactivestreams.Publisher;


@ApplicationScoped
public class EventService {
    Publisher<EventModel> events;
	Multi<EventModel> eventBroadcast;
	Emitter<EventModel> eventEmitter;
    Vertx vertx;
    
    public EventService(
			@Channel("event-create") Emitter<EventModel> eventEmitter,
			@Channel("events") Publisher<EventModel> events,
			@Context Vertx vertx
	) {
		this.events = events;
		this.eventEmitter = eventEmitter;
		this.vertx = vertx;
        //merge 2 multi's and turn off smalrye to be able to use memery streams while testing
		eventBroadcast = Multi
				.createFrom()
					.publisher(events)
				.broadcast()
					.toAllSubscribers();
    }

    
    public Response createEvent(Double lat, Double lon, Long typeId, String description) {
		EventType type = EventType.findById(typeId);

		if(type == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		EventModel model = new EventModel();
		model.lat = lat;
		model.lon = lon;
		model.type = type;
		model.description = description;
		
		model.persist();

		if (eventEmitter != null) {
			model.isUpdate = false;
			eventEmitter.send(model);
		}

		return Response.ok(model).build();
    }
    

    public Response updateEvent(Long id, Double lat, Double lon, EventStatus status, Long typeId, String description) {

		EventModel update = EventModel.findById(id);
		EventType type = EventType.findById(typeId);
		
		if(update == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		if(type == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}


		update.status = status;
		update.description = description;
		update.lat = lat;
		update.lon = lon;
		update.type = type;
		update.persist();

		update.isUpdate = true;
		eventEmitter.send(update);

		return Response.ok(update).build();

    }
    

    public Uni<String> eventsPage() {
		return vertx
				.fileSystem()
				.readFile("/META-INF/resources/stream.html")
				.onItem()
				.apply(buffer -> buffer.toString("UTF-8"));
	}


    public static Multi<EventModel> addTypeFilter(MultivaluedMap<String, String> params, Multi<EventModel> stream) {
		try {
			long typeId = Long.parseLong(params.getFirst("type"));

			return stream.transform().byFilteringItemsWith(e -> e.type.id == typeId);
		}
		catch (Exception e) {
			return stream;
		}	
	}

	public static Multi<EventModel> addRegionFilter(MultivaluedMap<String, String> params, Multi<EventModel> stream) {
		try {
			double sx = Double.parseDouble(params.getFirst("sx"));
			double sy = Double.parseDouble(params.getFirst("sy"));
			double ex = Double.parseDouble(params.getFirst("ex"));
			double ey = Double.parseDouble(params.getFirst("ey"));

			return stream
				.transform()
				.byFilteringItemsWith(e -> 
					e.lon > sx &&
					e.lat > sy &&
					e.lon < ex &&
					e.lat < ey
				);
		}
		catch (Exception e) {
			return stream;
		}
    }
    
    public Multi<EventModel> events(UriInfo ui) {

		if(ui == null) {
			return eventBroadcast;
		}

		var queryParams = ui.getQueryParameters();
		var stream = eventBroadcast;

		stream = addTypeFilter(queryParams, stream);
		stream = addRegionFilter(queryParams, stream);

		return stream;
	}

}