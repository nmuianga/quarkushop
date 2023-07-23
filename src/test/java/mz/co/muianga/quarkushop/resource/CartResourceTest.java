package mz.co.muianga.quarkushop.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.delete;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartResourceTest {

    @Test
    @Order(1)
    void findAll() {
        when()
                .get("/carts")
                .then()
                .statusCode(OK.getStatusCode())
                .body("size()", greaterThan(0));
    }

    @Test
    @Order(2)
    void findAllActiveCharts() {
        get("/carts/active")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    @Order(3)
    void testGetActiveChartsForCustomer() {
        get("/carts/customer/3")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(OK.getStatusCode())
                .body(containsString("Yuran"));
    }

    @Test
    @Order(4)
    void testFindById() {
        get("/carts/3")
                .then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Muianga"))
                .body(containsString("NEW"));

        get("/carts/100")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    @Order(5)
    void testDelete() {
        get("/carts/1").then()
                .statusCode(OK.getStatusCode())
                .body(containsString("NEW"))
                .body(containsString("Nilvandro"));

        delete("/carts/1").then()
                .statusCode(NO_CONTENT.getStatusCode());

        get("/carts/1").then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Nilvandro"))
                .body(containsString("CANCELED"));
    }
}
