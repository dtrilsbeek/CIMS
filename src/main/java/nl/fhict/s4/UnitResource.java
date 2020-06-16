package nl.fhict.s4;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.quarkus.panache.common.Parameters;
import nl.fhict.s4.models.Team;
import nl.fhict.s4.models.Unit;

@Path("units")
@RequestScoped
public class UnitResource {
    
    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUnit(@PathParam("id") Long id) {

        Unit unit = Unit.findById(id);
        if(unit == null) {
            return Response.status(404).build();
        }
        unit.delete();
        return Response.noContent().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addUnit(@FormParam("name") String name, @FormParam("teamId") Long teamId) {

        if(Unit.count("name = :name", Parameters.with("name", name)) > 0) {
            //return a conflict response if there is a unit with the same name
            return Response.status(Status.CONFLICT).build();
        }
        else if(Team.count("id = :id", Parameters.with("id", teamId)) == 0) {
            //return a bad request response if the given team does not exist
            return Response.status(Status.BAD_REQUEST).build();
        }

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

        if(unit == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        if(team == null){
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        unit.name = name;
        unit.team = team;
        return Response.ok(unit).build();
        
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnits(@QueryParam("teamId") Long teamId) {
        if(teamId == null) {
            return Response.ok(Unit.listAll()).build();
        }
        else {
            Team team = Team.findById(teamId);
            return Response.ok(Unit.list("team = :team", Parameters.with("team", team))).build();
        }
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