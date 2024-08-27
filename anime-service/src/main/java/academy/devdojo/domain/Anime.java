package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {

    @EqualsAndHashCode.Include
    private Long id;
    @JsonProperty(value = "name")
    private String name;

}
