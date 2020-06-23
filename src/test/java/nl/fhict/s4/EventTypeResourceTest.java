package nl.fhict.s4;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.quarkus.test.junit.QuarkusTest;
import nl.fhict.s4.models.EventType;
import nl.fhict.s4.services.EventTypeService;

@QuarkusTest
@Transactional
public class EventTypeResourceTest {

    @Inject EventTypeService eventTypeService;

    EventType eventType1;

    @BeforeEach
    public void initDB() {
        eventType1 = new EventType();
        eventType1.name = "type1";
        eventType1.description = "type1";
        eventType1.icon = "icon";
        eventType1.persist();
    }

    @AfterEach
    public void resetDB() {
        EventType.deleteAll();
    }

    @Test
    void getAllEventTypes() {
        Response result = eventTypeService.getEventTypes();
        List<?> resultValue = (List<?>)result.getEntity();
        

        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals(1, resultValue.size()); 
    }

    @Test
    void getEventTypeById() {
        Response result = eventTypeService.getEventTypeById(eventType1.id);
        EventType resultValue = (EventType)result.getEntity();
        
        
        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals("type1", resultValue.name);
    }

    @Test
    void addEventType() {
        Response result = eventTypeService.addEventType("type2", "type2", "icon2");
        EventType resultValue = (EventType)result.getEntity();
        

        assertEquals(Status.OK.getStatusCode(), result.getStatus());
        assertEquals("type2", resultValue.name);
        assertEquals("type2", resultValue.description);
        assertEquals("icon2", resultValue.icon);

    }

    @Test
    void addEventTypeNameExists() {
        Response result = eventTypeService.addEventType(eventType1.name, eventType1.description, eventType1.icon);

        
        assertEquals(Status.CONFLICT.getStatusCode(), result.getStatus());
    }

    @Test
    void deleteEventType() {
        Response result = eventTypeService.deleteEventType(eventType1.id);
          
        
        assertEquals(Status.NO_CONTENT.getStatusCode(), result.getStatus());
    }

}