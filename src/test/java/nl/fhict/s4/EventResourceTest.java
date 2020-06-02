package nl.fhict.s4;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import nl.fhict.s4.models.EventModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;

@QuarkusTest
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
	}

	@AfterEach
	public void resetDB() {
		EventModel.deleteAll();
	}

	// test new team valid
	@Test
	public void testAddEvent() {
		event = new EventModel();
		event.type = 3;
		event.lon = 52.22349;
		event.lat = 43.220011;
		event.description = "Test Event 2";

		EventModel result = given()
				.when()
				.body(event)
				.contentType(ContentType.JSON)
				.post("/events").then().statusCode(200)
				.contentType(ContentType.JSON)
				.extract()
				.response()
				.jsonPath()
				.getObject("$", EventModel.class);

		assertNotNull(result.id);
	}

	// test new team invalid

	// test update team valid

	// test update team invalid

	// test get stream

	// test get stream invalid


	@Test
	public void testGetEndpoint() {
		given()
			.when().get("/hello")
			.then()
			.statusCode(404);
//			.body(is("hello"));
	}

}