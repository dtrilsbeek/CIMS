package nl.fhict.s4.resources;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;


import nl.fhict.s4.models.EventModel;
import nl.fhict.s4.models.EventStatus;

import nl.fhict.s4.services.EventService;

import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.annotations.cache.Cache;


import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;




@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Path("/events")
public class EventResource {

	@Inject EventService eventService;


	@GET
	@Path("/stream")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@SseElementType(MediaType.APPLICATION_JSON)
	public Multi<EventModel> events(@Context UriInfo ui) {
		return eventService.events(ui);
	}


	@POST
	@Authenticated
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createEvent(
		@FormParam("lat") double lat, 
		@FormParam("lon") double lon, 
		@FormParam("typeId") long typeId, 
		@FormParam("description") String description
	) 
	{
		return eventService.createEvent(lat, lon, typeId, description);
	}

	@PUT
	@Authenticated
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateEvent(
		@FormParam("id") long id,
		@FormParam("lat") double lat, 
		@FormParam("lon") double lon, 
		@FormParam("status") EventStatus status,
		@FormParam("typeId") long typeId, 
		@FormParam("description") String description
	) 
	{
		return eventService.updateEvent(id, lat, lon, status, typeId, description);	
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Cache(noStore = false, isPrivate = false, maxAge = 31_536_000)
	public Uni<String> eventsPage() {
		return eventService.eventsPage();
	}
}