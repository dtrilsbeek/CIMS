package nl.fhict.s4;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.fhict.s4.models.Team;
import nl.fhict.s4.models.Unit;

@Path("units")
@RequestScoped
public class UnitResource {
    @DELETE
    @Path("{id}")
    public Response deleteUnit(@PathParam("id") int id) {
        //TODO: HANDLE NULL?
        //TODO: STATUS AND RETURN VALUE OF DELETE?
        Unit.findById(id).delete();
        return Response.noContent().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addUnit(@FormParam("name") String name, @FormParam("teamId") Long teamId) {
        Unit unit = Unit.addUnit(name, teamId);

        return Response.ok(unit).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response updateUnit(@FormParam("name") String name, @FormParam("teamId") Long teamId, @FormParam("unitId") Long unitId){
        Unit unit = Unit.findById(unitId);
        Team team = Team.findById(teamId);

        if(unit == null || team == null){
            return Response.noContent().build();
        }
        else{
            unit.name = name;
            unit.team = team;
            return Response.ok(unit).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnits() {
        return Response.ok(Unit.listAll()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUnitById(@PathParam("id") Long id) {
        //TODO: STATUS AND RETURN VALUE OF DELETE?
        Response response = Response.noContent().build();
        Unit unit = Unit.findById(id);
        if(unit != null) {
            response = Response.ok(unit).build();
        }
        return response;
    }

}