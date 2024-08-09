package academy.devdojo.animeservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimePutRequest {
    @NotNull
    private Long id;
    @NotBlank(message = "The field 'name' is required")
    private String name;
}
