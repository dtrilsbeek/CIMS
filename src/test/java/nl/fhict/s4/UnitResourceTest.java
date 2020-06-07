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
import nl.fhict.s4.models.Unit;

@QuarkusTest
@Transactional
public class UnitResourceTest {
    Team team1;
    Unit unit1;
    Unit unit2;

    @BeforeEach
    public void initDB() {
        team1 = new Team();
        team1.name = "team1";
        team1.persist();

        unit1 = new Unit();
        unit1.name = "unit1";
        unit1.team = team1;
        unit1.persist();

        unit2 = new Unit();
        unit2.name = "unit2";
        unit2.team = team1;
        unit2.persist();
    }

    @AfterEach
    public void resetDB() {
        Unit.deleteAll();
        Team.deleteAll();
    }

    @Test
    void testGetAllUnits() {
        int typeCount = given()
            .when()
            .get("/units")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .jsonPath()
            .getList("$")
            .size();
        assertEquals(typeCount, 2);
    }

    @Test
    void testGetUnitById() {
        Unit result = given()
            .when()
            .get("/units/" + unit1.id)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .jsonPath()
            .getObject("$", Unit.class);
        assertEquals(result.name, "unit1");
    }

    @Test
    void testAddUnit() {
        Unit result = given()
            .when()
            .urlEncodingEnabled(true)
            .param("name", "unit3")
            .param("teamId", team1.id)
            .post("/units").then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .response()
            .jsonPath()
            .getObject("$", Unit.class);
        assertEquals(result.name, "unit3");
    }

    @Test
    void testAddUnitNameExists() {
        given()
            .when()
            .urlEncodingEnabled(true)
            .param("name", "unit1")
            .param("teamId", team1.id)
            .post("/units/").then()
            .statusCode(409);
    }

    @Test
    void testAddUnitInvalidTeamId() {
        given()
            .when()
            .urlEncodingEnabled(true)
            .param("name", "unit3")
            .param("teamId", 1234567)
            .post("/units/").then()
            .statusCode(409);
    }

    @Test
    void testDeleteUnit() {
        given().delete("/units/" + unit1.id)
            .then()
            .statusCode(204);
    }
}
