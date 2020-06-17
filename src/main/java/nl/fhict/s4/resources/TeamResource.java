package nl.fhict.s4.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;




import nl.fhict.s4.services.TeamService;

@Path("teams")
@RequestScoped
public class TeamResource {
    
    @Inject TeamService teamService;

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteTeam(@PathParam("id") Long id) {
        return teamService.deleteTeam(id);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addTeam(@FormParam("name") String name) {
        return teamService.addTeam(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeams() {
       return teamService.getTeams();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getTeamById(@PathParam("id") Long id) {
        return teamService.getTeamById(id);
    }

}