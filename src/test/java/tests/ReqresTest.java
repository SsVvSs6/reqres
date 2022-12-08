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

    private static final String USER_NAME = "morpheus";
    private static final String CREATE_USER_JOB = "leader";
    private static final String UPDATE_USER_JOB = "zion resident";
    private static final String SUCCESSFUL_EMAIL = "eve.holt@reqres.in";
    private static final String UNSUCCESSFUL_REGISTER_EMAIL = "sydney@fife";
    private static final String UNSUCCESSFUL_LOGIN_EMAIL = "peter@klaven";
    private static final String REGISTER_PASSWORD = "pistol";
    private static final String LOGIN_PASSWORD = "cityslicka";
    private static final String MISSING_PASSWORD_ERROR = "Missing password";

    @Test
    public void postCreateUserTest() {
        User user = User.builder()
                .name(USER_NAME)
                .job(CREATE_USER_JOB)
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
        int expectedResourceId = 2;
        String expectedResourceName = "fuchsia rose";
        int expectedResourceYear = 2001;
        String expectedResourceColor = "#C74375";
        String expectedResourcePantoneValue = "17-2031";
        String supportUrl = "https://reqres.in/#support-heading";
        String supportText = "To keep ReqRes free, contributions towards server costs are appreciated!";
        SingleResource expectedSingleResource = SingleResource.builder()
                .data(ResourceData.builder()
                        .id(expectedResourceId)
                        .name(expectedResourceName)
                        .year(expectedResourceYear)
                        .color(expectedResourceColor)
                        .pantone_value(expectedResourcePantoneValue)
                        .build())
                .support(ReqResSupport.builder()
                        .url(supportUrl)
                        .text(supportText)
                        .build())
                        .build();
        String singleResourceId = "2";
        String body = new ResourceAdapter().getSingleResource(singleResourceId).body().asString();
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
                .name(USER_NAME)
                .job(UPDATE_USER_JOB)
                .build();
        Response response = new UserAdapter().putUpdateUser(userId, user);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void patchUpdateUserTest() {
        String userId = "2";
        User user = User.builder()
                .name(USER_NAME)
                .job(UPDATE_USER_JOB)
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
                .email(SUCCESSFUL_EMAIL)
                .password(REGISTER_PASSWORD)
                .build();
        Response response = new UserAdapter().registerUser(user);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void postUnsuccessfulRegisterTest() {
        User user = User.builder()
                .email(UNSUCCESSFUL_REGISTER_EMAIL)
                .build();
        Response response = new UserAdapter().registerUser(user);
        ResponseError expectedError = ResponseError.builder()
                .error(MISSING_PASSWORD_ERROR)
                .build();
        ResponseError actualError = new Gson().fromJson(response.getBody().asString(), ResponseError.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
        softAssert.assertEquals(actualError, expectedError);
    }

    @Test
    public void postSuccessfulLoginTest() {
        User user = User.builder()
                .email(SUCCESSFUL_EMAIL)
                .password(LOGIN_PASSWORD)
                .build();
        Response response = new UserAdapter().loginUser(user);
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void postUnsuccessfulLoginTest() {
        User user = User.builder()
                .email(UNSUCCESSFUL_LOGIN_EMAIL)
                .build();
        Response response = new UserAdapter().loginUser(user);
        ResponseError expectedError = ResponseError.builder()
                .error(MISSING_PASSWORD_ERROR)
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
