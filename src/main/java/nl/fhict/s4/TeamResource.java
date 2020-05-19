package nl.fhict.s4;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.fhict.s4.models.Team;

@Path("teams")
@RequestScoped
public class TeamResource {
    @DELETE
    @Path("{id}")
    public Response deleteTeam(@PathParam("id") int id) {
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

}