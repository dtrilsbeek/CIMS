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
import nl.fhict.s4.models.Unit;
import nl.fhict.s4.services.UnitService;

@QuarkusTest
@Transactional
public class UnitResourceTest {

    @Inject UnitService unitService;

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
        Response result = unitService.getUnits(null);
        List<?> resultValue = (List<?>)result.getEntity();
        

        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals(2, resultValue.size());
    }

    @Test
    void testGetUnitById() {
        Response result = unitService.getUnitById(unit1.id);
        Unit resultValue = (Unit)result.getEntity();
        
        
        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals(resultValue.name, "unit1");
    }

    @Test
    void testAddUnit() {
        Response result = unitService.addUnit("unit3", team1.id);
        Unit resultValue = (Unit)result.getEntity();
        

        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals(resultValue.team.name, "team1");
        assertEquals(resultValue.name, "unit3");
    }

    @Test
    void testAddUnitNameExists() {
        Response result = unitService.addUnit("unit1", team1.id);

        
        assertEquals(Status.CONFLICT.getStatusCode(), result.getStatus());
    }

    @Test
    void testAddUnitInvalidTeamId() {
        Response result = unitService.addUnit("unit3", (long)1234567);
          
        
        assertEquals(Status.BAD_REQUEST.getStatusCode(), result.getStatus());
    }

    @Test
    void testDeleteUnit() {
        Response result = unitService.deleteUnit(unit1.id);
          
        
        assertEquals(Status.NO_CONTENT.getStatusCode(), result.getStatus());
    }
}
