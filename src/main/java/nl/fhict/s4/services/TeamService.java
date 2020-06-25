package nl.fhict.s4.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.quarkus.panache.common.Parameters;
import nl.fhict.s4.models.Team;

@ApplicationScoped
public class TeamService {
    public Response deleteTeam(Long id) {

        Team team = Team.findById(id);
        if(team == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        team.delete();
        return Response.noContent().build();
    }

    public Response addTeam(String name) {

        if(Team.count("name = :name", Parameters.with("name", name)) > 0) {
            //return a conflict response if the team already exists
            return Response.status(Status.CONFLICT).build();
        }   

        Team team = new Team();
        team.name = name;
        team.persist();

        return Response.ok(team).build();
    }

    public Response getTeams() {
        return Response.ok(Team.listAll()).build();
    }

    public Response getTeamById(Long id) {
        Team team = Team.findById(id);

        if(team == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(team).build();
    }

}