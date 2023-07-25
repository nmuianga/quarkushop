package mz.co.muianga.quarkushop.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.sql.SQLException;
import java.util.Map;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mz.co.muianga.quarkushop.model.CartStatus;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.RestAssured.when;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
class CartResourceTest {

    private static final String INSERT_WRONG_CART_IN_DB = "insert into carts (id, customer_id, status, created_date, last_modified_date) values (999, 3, 'NEW', current_timestamp, current_timestamp)";
    private static final String DELETE_WRONG_CART_IN_DB = "delete from carts where id = 999";

    @Inject
    DataSource dataSource;

    @Test
    void findAll() {
        when()
                .get("/carts")
                .then()
                .statusCode(OK.getStatusCode())
                .body("size()", greaterThan(0));
    }

    @Test
    void findAllActiveCharts() {
        get("/carts/active")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testGetActiveChartsForCustomer() {
        get("/carts/customer/3")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(OK.getStatusCode())
                .body(containsString("Yuran"));
    }

    @Test
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

    /*@Test
    void testGetActiveCartForCustomerWhenThereAreTwoCartsInDB() {
        executeSql(INSERT_WRONG_CART_IN_DB);

        get("/carts/customer/3")
                .then()
                .statusCode(INTERNAL_SERVER_ERROR.getStatusCode())
                //.body(containsString(INTERNAL_SERVER_ERROR.getReasonPhrase()))
                .body(containsString("Many active carts detected !!!"));
        executeSql(DELETE_WRONG_CART_IN_DB);
    }*/

    /*@Test
    void testCreateCart() {
        var requestParams = Map.of("firstName", "Saul", "lastName", "Berenson", "email", "call.saul@mail.com");

        var newCustomerId = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(requestParams)
                .post("/customers")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath().getInt("id");

        var response = post("/carts/customer/" + newCustomerId)
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath()
                .getMap("$");

        assertThat(response.get("id")).isNotNull();
        assertThat(response).containsEntry("status", CartStatus.NEW.name());

        delete("/carts/" + response.get("id")).then()
                .statusCode(NO_CONTENT.getStatusCode());

        delete("/customers/" + newCustomerId).then()
                .statusCode(NO_CONTENT.getStatusCode());
    }*/

    private void executeSql(String query) {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error has occurred while trying to execute SQL Query: " + e.getMessage());
        }
    }
}
