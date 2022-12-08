package objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceData {

    private int id;
    private String name;
    private int year;
    private String color;
    private String pantone_value;
}
