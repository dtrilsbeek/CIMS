package nl.fhict.s4.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.fhict.s4.services.UnitService;

@Path("units")
@RequestScoped
public class UnitResource {
    
    @Inject UnitService unitService;

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUnit(@PathParam("id") Long id) {
        return unitService.deleteUnit(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addUnit(@FormParam("name") String name, @FormParam("teamId") Long teamId) {
        return unitService.addUnit(name, teamId);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response updateUnit(@FormParam("name") String name, @FormParam("teamId") Long teamId, @FormParam("unitId") Long unitId){
        return unitService.updateUnit(name, teamId, unitId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnits(@QueryParam("teamId") Long teamId) {
        return unitService.getUnits(teamId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUnitById(@PathParam("id") Long id) {
       return unitService.getUnitById(id);
    }

}