package nl.fhict.s4;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class EventResourceTest {

	@Test
	public void testGetEndpoint() {
		given()
			.when().get("/hello")
			.then()
			.statusCode(404);
//			.body(is("hello"));
	}

}