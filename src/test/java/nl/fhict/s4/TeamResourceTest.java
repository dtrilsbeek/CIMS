package nl.fhict.s4;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import nl.fhict.s4.models.Team;

@QuarkusTest
@Transactional
public class TeamResourceTest {

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
        int typeCount = given()
        .when()
            .get("/teams")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .jsonPath()
            .getList("$")
            .size();

        assertEquals(typeCount, 1);    
    }

    @Test
    void testGetTeamById() {
        Team result = given()
        .when()
            .get("/teams/" + team1.id)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .jsonPath()
            .getObject("$", Team.class);

        assertEquals(result.name, "team1");
    }

    @Test
    void testAddTeam() {
        Team result = given()
        .when()
        .urlEncodingEnabled(true)
        .param("name", "team2")
        .post("/teams").then()
        .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .jsonPath()
            .getObject("$", Team.class);

            assertEquals(result.name, "team2");
    }

    @Test
    public void addTeamNameExists() {
        given()
            .when()
            .urlEncodingEnabled(true)
            .param("name", "team1")
            .post("/teams/").then()
            .statusCode(409);
    }

    @Test
    void testDeleteTeam() {
        given().delete("/teams/" + team1.id)
        .then()
            .statusCode(204);
    }

}