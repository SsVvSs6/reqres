package adapter;

import io.restassured.response.Response;
import objects.User;

import static utils.StringConstant.*;

public class UserAdapter extends BaseAdapter {

    public Response createUser(User user) {
        return post(USERS_API_ENDPOINT, converter.toJson(user));
    }

    public Response getUsersList(String page) {
        return get(String.format(USERS_LIST_API_ENDPOINT, page));
    }

    public Response getSingleUser(String userId) {
        return get(String.format(SINGLE_USER_API_ENDPOINT, userId));
    }

    public Response putUpdateUser(String userId, User user) {
        return put(String.format(SINGLE_USER_API_ENDPOINT, userId), converter.toJson(user));
    }

    public Response patchUpdateUser(String userId, User user) {
        return patch(String.format(SINGLE_USER_API_ENDPOINT, userId), converter.toJson(user));
    }

    public Response deleteUser(String userId) {
        return delete(String.format(SINGLE_USER_API_ENDPOINT, userId));
    }

    public Response registerUser(User user) {
        return post(USER_REGISTER_API_ENDPOINT, converter.toJson(user));
    }

    public Response loginUser(User user) {
        return post(USER_LOGIN_API_ENDPOINT, converter.toJson(user));
    }

    public Response getUsersDelay(String page) {
        return get(String.format(USERS_DELAY_API_ENDPOINT, page));
    }
}
