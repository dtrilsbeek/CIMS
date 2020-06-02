package nl.fhict.s4;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import nl.fhict.s4.models.EventModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.awaitility.Awaitility.await;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.restassured.RestAssured.given;

@QuarkusTest
//@QuarkusTestResource(KafkaResource.class)
public class EventResourceTest {

	EventModel event;
	Jsonb jsonb;

	@BeforeEach
	@Transactional
	public void initDB() {
		jsonb = JsonbBuilder.create(new JsonbConfig());

		event = new EventModel();
		event.type = 2;
		event.lon = 52.22349;
		event.lat = 43.220011;
		event.description = "Test Event";
		event.persist();

		System.out.println(event.id);
	}

	@AfterEach
	@Transactional
	public void resetDB() {
		EventModel.deleteAll();
	}

	@Test
	public void testAddEvent() {
		event = new EventModel();
		event.type = 3;
		event.lon = 52.22349;
		event.lat = 43.220011;
		event.description = "Test Event 2";

		EventModel result = given()
				.when()
				.body(jsonb.toJson(event))
				.contentType(ContentType.JSON)
				.post("/events").then().statusCode(200)
				.contentType(ContentType.JSON)
				.extract()
				.response()
				.jsonPath()
				.getObject("$", EventModel.class);

		assertNotNull(result.id);
	}

	@Test
	public void testUpdateEvent() {
		event = new EventModel();
		event.id = 2L;
		event.type = 5;
		event.lon = 52.22349;
		event.lat = 43.220011;
		event.description = "Test Event 5";

		String result = given()
				.when()
				.body(jsonb.toJson(event))
				.contentType(ContentType.JSON)
				.put("/events").then().statusCode(200)
				.contentType(ContentType.JSON)
				.extract()
				.response().asString();

		EventModel resultEvent = jsonb.fromJson(result, EventModel.class);

		assertEquals(5, resultEvent.type);
	}

	private static final String PRICES_SSE_ENDPOINT = "http://localhost:8081/events/stream";

	@Test
	void testPricesEventStream() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(PRICES_SSE_ENDPOINT);

		List<String> received = new CopyOnWriteArrayList<>();

		SseEventSource source = SseEventSource.target(target).build();
		source.register(inboundSseEvent -> {
			received.add(inboundSseEvent.readData());
		});
		source.open();
		await().atMost(100000, MILLISECONDS).until(() -> received.size() == 3);
		source.close();
	}

}