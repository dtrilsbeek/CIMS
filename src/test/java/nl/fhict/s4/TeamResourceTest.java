package nl.fhict.s4;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import nl.fhict.s4.models.Team;
import nl.fhict.s4.services.TeamService;

@QuarkusTest
@Transactional
public class TeamResourceTest {

    @Inject TeamService teamService;
    
    Team team1;

    @BeforeEach
    public void initDB() {
        team1 = new Team();
        team1.name = "team1";
        team1.persist();
    }

    @AfterEach
    public void resetDB() {
        Team.deleteAll();
    }

    @Test
    void testGetAllTeams() {
        Response result = teamService.getTeams();
        List<?> resultValue = (List<?>)result.getEntity();
        

        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals(1, resultValue.size()); 
    }

    @Test
    void testGetTeamById() {
        Response result = teamService.getTeamById(team1.id);
        Team resultValue = (Team)result.getEntity();
        
        
        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals(resultValue.name, "team1");
    }

    @Test
    void testAddTeam() {
        Response result = teamService.addTeam("team2");
        Team resultValue = (Team)result.getEntity();
        

        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals(resultValue.name, "team2");
    }

    @Test
    public void addTeamNameExists() {
        Response result = teamService.addTeam(team1.name);
        

        assertEquals(Status.CONFLICT.getStatusCode(), result.getStatus());
    }

    @Test
    void testDeleteTeam() {
        Response result = teamService.deleteTeam(team1.id);

        
        assertEquals(Status.NO_CONTENT.getStatusCode(), result.getStatus());
    }

}