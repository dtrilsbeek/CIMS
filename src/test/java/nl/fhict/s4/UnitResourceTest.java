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

    @BeforeEach
    public void initDB() {
        team1 = new Team();
        team1.name = "team1";
        team1.persist();

        unit1 = new Unit();
        unit1.name = "unit1";
        unit1.team = team1;
        unit1.persist();
    }

    @AfterEach
    public void resetDB() {
        Unit.deleteAll();
        Team.deleteAll();
    }

    @Test
    void getAllUnits() {

    }

    @Test
    void getUnitById() {

    }

    @Test
    void addUnit() {

    }

    @Test
    void addUnitNameExists() {

    }

    @Test
    void addUnitInvalidTeamId() {
    }
    

    @Test
    void deleteUnit() {

    }
}