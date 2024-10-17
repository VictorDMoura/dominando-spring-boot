package academy.devdojo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfilePostResponse {
    private long id;
    private String name;
    private String description;
}

