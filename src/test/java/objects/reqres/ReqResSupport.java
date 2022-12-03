package objects.reqres;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqResSupport {

    private String url;
    private String text;
}
