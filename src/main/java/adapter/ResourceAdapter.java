package adapter;

import io.restassured.response.Response;

import static utils.StringConstant.*;

public class ResourceAdapter extends BaseAdapter {

    public Response getResourceList() {
        return get(RESOURCE_API_ENDPOINT);
    }

    public Response getSingleResource(String resourceId) {
        return get(String.format(SINGLE_RESOURCE_API_ENDPOINT, resourceId));
    }
}
