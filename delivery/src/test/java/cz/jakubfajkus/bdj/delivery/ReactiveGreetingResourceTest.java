package cz.jakubfajkus.bdj.delivery;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class ReactiveGreetingResourceTest {

    @Test
    public void testGetAllDeliveriesReturnsDefaultDelivery() {
        given()
                .when().get("/delivery")
                .then()
                .statusCode(200)
                .body(containsString("Bun bo Nam Bo: 1ks"));
    }

    @Test
    public void testGetSingleDeliveryReturnsDefaultDelivery() {
        given()
                .when().get("/delivery/ae76a6b6-cf3e-4a5a-9888-ac0bdff0ec2b")
                .then()
                .statusCode(200)
                .body(Delivery.Fields.id, equalTo("ae76a6b6-cf3e-4a5a-9888-ac0bdff0ec2b"))
                .body(Delivery.Fields.restaurantId, equalTo("c59121fb-e62f-4126-8aa9-f0a60af1120e"))
                .body(Delivery.Fields.orderContent, equalTo("Bun bo Nam Bo: 1ks"))
                .body(Delivery.Fields.customerId, equalTo("72612a7e-6feb-4fdb-af4e-48940171657c"))
                .body(Delivery.Fields.customerName, equalTo("Pepa Novak"))
                .body(Delivery.Fields.street, equalTo("Ulice 33"))
                .body(Delivery.Fields.city, equalTo("Brno"))
                .body(Delivery.Fields.zip, equalTo("61500"))
                .body(Delivery.Fields.state, equalTo(Delivery.State.CREATED.name()));
    }

    @Test
    public void testCreateDeliveryCreatesDelivery() {
        String location = given()
                .when().body(Map.of("restaurantId", "rest-xxx", "orderContent", "createTest", "city", "brno", "customerName", "Pepa novak", "zip", "61500", "customerId", "cust-321", "street", "ulice 23"))
                .contentType("application/json")
                .post("/delivery")
                .then()
                .statusCode(201)
                .extract().header("Location");

        given()
                .when().get(location)
                .then()
                .statusCode(200)
                .body(Delivery.Fields.restaurantId, equalTo("rest-xxx"))
                .body(Delivery.Fields.orderContent, equalTo("createTest"))
                .body(Delivery.Fields.customerId, equalTo("cust-321"));
    }

    @Test
    public void testChangeState() {

        given()
                .when().body("\"DELIVERED\"")
                .contentType("application/json")
                .patch("/delivery/ae76a6b6-cf3e-4a5a-9888-ac0bdff0ec2b/state")
                .then()
                .statusCode(200)
                .body(Delivery.Fields.state, equalTo("DELIVERED"));
    }

}