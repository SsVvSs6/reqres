package utils;

public class StringConstant {
    public static final String BASE_URL = "https://reqres.in/api";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String JSON = "application/json";
    public static final String USERS_API_ENDPOINT = "/users";
    public static final String USERS_LIST_API_ENDPOINT = USERS_API_ENDPOINT + "?page=%s";
    public static final String USERS_DELAY_API_ENDPOINT = USERS_API_ENDPOINT + "?delay=%s";
    public static final String SINGLE_USER_API_ENDPOINT = USERS_API_ENDPOINT + "/%s";
    public static final String USER_REGISTER_API_ENDPOINT = "/register";
    public static final String USER_LOGIN_API_ENDPOINT = "/login";

    public static final String RESOURCE_API_ENDPOINT = "/unknown";

    public static final String SINGLE_RESOURCE_API_ENDPOINT = RESOURCE_API_ENDPOINT + "/%s";

    private StringConstant() {
    }
}
