package nl.fhict.s4;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.panache.common.Parameters;
import nl.fhict.s4.models.EventType;

@Path("types")
@RequestScoped
public class EventTypeResource {
    
    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteEventType(@PathParam("id") long id) {
        //TODO: HANDLE NULL?
        //TODO: STATUS AND RETURN VALUE OF DELETE?
        EventType.findById(id).delete();
        return Response.noContent().build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addEventType(@FormParam("name") String name, @FormParam("description") String description,  @FormParam("icon") String icon) {

        if(EventType.count("name = :name", Parameters.with("name", name)) > 0) {
            //return a conflict response if the EventType already exists
            return Response.status(409).build();
        }

        EventType eventType = new EventType();
        eventType.name = name;
        eventType.description = description;
        eventType.icon = icon;
        eventType.persist();

        return Response.ok(eventType).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventTypes() {
        return Response.ok(EventType.listAll()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getEventTypeById(@PathParam("id") Long id) {
        //TODO: HANDLE NULL?
        //TODO: STATUS AND RETURN VALUE OF DELETE?
        EventType eventType = EventType.findById(id);
        return Response.ok(eventType).build();
    }

}