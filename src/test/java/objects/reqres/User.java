package objects.reqres;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String name;
    private String job;
    private String email;
    private String password;
}
