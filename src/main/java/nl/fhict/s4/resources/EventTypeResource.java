package nl.fhict.s4.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.fhict.s4.services.EventTypeService;

@Path("types")
@RequestScoped
public class EventTypeResource {
    

    @Inject EventTypeService eventTypeService;

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteEventType(@PathParam("id") long id) {
        return eventTypeService.deleteEventType(id);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addEventType(@FormParam("name") String name, @FormParam("description") String description,  @FormParam("icon") String icon) {
        return eventTypeService.addEventType(name, description, icon);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventTypes() {
       return eventTypeService.getEventTypes();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getEventTypeById(@PathParam("id") Long id) {
      return eventTypeService.getEventTypeById(id);
    }

}