package nl.fhict.s4;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.panache.common.Parameters;
import nl.fhict.s4.models.Team;

@Path("teams")
@RequestScoped
public class TeamResource {
    
    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteTeam(@PathParam("id") long id) {
        //TODO: HANDLE NULL?
        //TODO: STATUS AND RETURN VALUE OF DELETE?
        Team.findById(id).delete();
        return Response.noContent().build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addTeam(@FormParam("name") String name) {

        if(Team.count("name = :name", Parameters.with("name", name)) > 0) {
            //return a conflict response if the team already exists
            return Response.status(409).build();
        }   

        Team team = new Team();
        team.name = name;
        team.persist();

        return Response.ok(team).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeams() {
        return Response.ok(Team.listAll()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getTeamById(@PathParam("id") Long id) {
        //TODO: HANDLE NULL?
        //TODO: STATUS AND RETURN VALUE OF DELETE?
        Team team = Team.findById(id);
        return Response.ok(team).build();
    }

}