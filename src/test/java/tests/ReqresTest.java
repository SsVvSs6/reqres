package tests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import objects.reqres.ResourceData;
import objects.reqres.ReqResSupport;
import objects.reqres.SingleResource;
import objects.reqres.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;

public class ReqresTest {

    @Test
    public void postCreateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();
        Response response = given()
                .body(user)
                .when()
                .log().all()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_CREATED);
    }

    @Test
    public void getListUsersTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleUserTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleUserNotFoundTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void getListResourceTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleResourceTest() {
        String body = given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .extract().body().asString();
        SingleResource expectedSingleResource = SingleResource.builder()
                .data(ResourceData.builder()
                        .id(2)
                        .name("fuchsia rose")
                        .year(2001)
                        .color("#C74375")
                        .pantone_value("17-2031")
                        .build())
                .support(ReqResSupport.builder()
                        .url("https://reqres.in/#support-heading")
                        .text("To keep ReqRes free, contributions towards server costs are appreciated!")
                        .build())
                        .build();
        SingleResource actualSingleResource = new Gson().fromJson(body, SingleResource.class);
        Assert.assertEquals(actualSingleResource, expectedSingleResource);
    }

    @Test
    public void getSingleResourceNotFoundTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void putUpdateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        Response response = given()
                .body(user)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void patchUpdateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        Response response = given()
                .body(user)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void deleteUserTest() {
        Response response = given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NO_CONTENT);
    }

    @Test
    public void postSuccessfulRegisterTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        Response response = given()
                .body(user)
                .when()
                .log().all()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void postUnsuccessfulRegisterTest() {
        User user = User.builder()
                .email("sydney@fife")
                .build();
        Response response = given()
                .body(user)
                .when()
                .log().all()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
    }

    @Test
    public void postSuccessfulLoginTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        Response response = given()
                .body(user)
                .when()
                .log().all()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void postUnsuccessfulLoginTest() {
        User user = User.builder()
                .email("peter@klaven")
                .build();
        Response response = given()
                .body(user)
                .when()
                .log().all()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
    }

    @Test
    public void getDelayedResponseTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }
}
