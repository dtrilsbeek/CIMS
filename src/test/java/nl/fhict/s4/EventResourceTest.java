package nl.fhict.s4;


import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;

import nl.fhict.s4.models.EventModel;
import nl.fhict.s4.models.EventStatus;
import nl.fhict.s4.models.EventType;
import nl.fhict.s4.services.EventService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@QuarkusTest
@QuarkusTestResource(KafkaResource.class)
@Transactional
public class EventResourceTest {

	@Inject EventService eventService;
	@Inject @Any InMemoryConnector connector;

	EventType type;
	EventModel event;


	@BeforeEach
	public void initDB() {
		type = new EventType();
		type.name = "ambulance";
		type.icon = "icon";
		type.description = "ambulance";
		type.persist();

		event = new EventModel();
		event.type = type;
		event.lon = 52.22349;
		event.lat = 43.220011;
		event.description = "Test Event";
		event.persist();
	}

	@AfterEach
	public void resetDB() {
		EventModel.deleteAll();
		EventType.deleteAll();
	}

	@Test
	public void testAddEvent() {

		Response result = eventService.createEvent(50.0, 5.0, type.id, "event");
        EventModel resultValue = (EventModel)result.getEntity();
        

        assertEquals(Status.OK.getStatusCode(), result.getStatus());
		assertEquals(50.0, resultValue.lat);
		assertEquals(5.0, resultValue.lon);
		assertEquals(type.id, resultValue.type.id);
        assertEquals("event", resultValue.description);
		assertNotNull(resultValue.id);
	}

	@Test
	public void testUpdateEvent() {
		EventType type2 = new EventType();
		type2.name = "firetruck";
		type2.description = "firetruck";
		type2.icon = "firetruckIcon";
		type2.persist();

		Response result = eventService.updateEvent(event.id, 25.0, 35.0, EventStatus.FINISHED, type2.id, "firetruck");
		EventModel resultValue = (EventModel)result.getEntity();
        

        assertEquals(Status.OK.getStatusCode(), result.getStatus());
		assertEquals(25.0, resultValue.lat);
		assertEquals(35.0, resultValue.lon);
		assertEquals(type2.id, resultValue.type.id);
        assertEquals("firetruck", resultValue.description);
		assertEquals(event.id, resultValue.id);
	}

	@Test
	public void filterEventsCoordInRange() {
		MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>(Map.of("sx", "1", "ex", "5", "sy", "1", "ey", "5"));
		Multi<EventModel> stream = Multi.createFrom().items(
			new EventModel(4.0, 4.0, type, ""),
			new EventModel(6.0, 4.0, type, ""),
			new EventModel(4.0, 6.0, type, "")
		);
		
		Multi<EventModel> result = EventService.addRegionFilter(params, stream);

		List<EventModel> received = new CopyOnWriteArrayList<>();

		result.subscribe().with(e -> received.add(e));
		
		await().atMost(1000, MILLISECONDS).until(() -> received.size() == 1);
	}

	@Test
	public void filterEventsMatchingType() {
		MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>(Map.of("typeId", type.id.toString()));
		Multi<EventModel> stream = Multi.createFrom().items(
			new EventModel(4.0, 4.0, type, ""),
			new EventModel(6.0, 4.0, type, ""),
			new EventModel(4.0, 6.0, type, "")
		);
		
		Multi<EventModel> result = EventService.addTypeFilter(params, stream);

		List<EventModel> received = new CopyOnWriteArrayList<>();

		result.subscribe().with(e -> received.add(e));;
		
		await().atMost(1000, MILLISECONDS).until(() -> received.size() == 3);
	}
}