package tests;

import adapter.ResourceAdapter;
import adapter.UserAdapter;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import objects.*;

import static java.net.HttpURLConnection.*;

public class ReqresTest {

    @Test
    public void postCreateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();
        Response response = new UserAdapter().createUser(user);
        Assert.assertEquals(response.statusCode(), HTTP_CREATED);
    }

    @Test
    public void getListUsersTest() {
        String page = "2";
        Response response = new UserAdapter().getUsersList(page);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleUserTest() {
        String userId = "2";
        Response response = new UserAdapter().getSingleUser(userId);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleUserNotFoundTest() {
        String userId = "23";
        Response response = new UserAdapter().getSingleUser(userId);
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void getListResourceTest() {
        Response response = new ResourceAdapter().getResourceList();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleResourceTest() {
        String resourceId = "2";
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
        String body = new ResourceAdapter().getSingleResource(resourceId).body().asString();
        SingleResource actualSingleResource = new Gson().fromJson(body, SingleResource.class);
        Assert.assertEquals(actualSingleResource, expectedSingleResource);
    }

    @Test
    public void getSingleResourceNotFoundTest() {
        String resourceId = "23";
        Response response = new ResourceAdapter().getSingleResource(resourceId);
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void putUpdateUserTest() {
        String userId = "2";
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        Response response = new UserAdapter().putUpdateUser(userId, user);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void patchUpdateUserTest() {
        String userId = "2";
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        Response response = new UserAdapter().patchUpdateUser(userId, user);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void deleteUserTest() {
        String userId = "2";
        Response response = new UserAdapter().deleteUser(userId);
        Assert.assertEquals(response.statusCode(), HTTP_NO_CONTENT);
    }

    @Test
    public void postSuccessfulRegisterTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        Response response = new UserAdapter().registerUser(user);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void postUnsuccessfulRegisterTest() {
        User user = User.builder()
                .email("sydney@fife")
                .build();
        Response response = new UserAdapter().registerUser(user);
        ResponseError expectedError = ResponseError.builder()
                .error("Missing password")
                .build();
        ResponseError actualError = new Gson().fromJson(response.getBody().asString(), ResponseError.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
        softAssert.assertEquals(actualError, expectedError);
    }

    @Test
    public void postSuccessfulLoginTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        Response response = new UserAdapter().loginUser(user);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void postUnsuccessfulLoginTest() {
        User user = User.builder()
                .email("peter@klaven")
                .build();
        Response response = new UserAdapter().loginUser(user);
        ResponseError expectedError = ResponseError.builder()
                .error("Missing password")
                .build();
        ResponseError actualError = new Gson().fromJson(response.getBody().asString(), ResponseError.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
        softAssert.assertEquals(actualError, expectedError);
    }

    @Test
    public void getDelayedResponseTest() {
        String page = "3";
        Response response = new UserAdapter().getUsersDelay(page);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }
}
