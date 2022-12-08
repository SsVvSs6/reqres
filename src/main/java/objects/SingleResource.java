package objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingleResource {

    private ResourceData data;
    private ReqResSupport support;
}
