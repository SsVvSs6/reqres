package objects.reqres;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseError {

    private String error;
}
