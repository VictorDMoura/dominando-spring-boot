package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@AllArgsConstructor
@ToString
public class Anime {
    private Long id;
    @JsonProperty(value = "name")
    private String name;


    public static List<Anime> list() {
        return List.of(new Anime(1L, "Boku no Hero"),
                new Anime(2L, "Berserk"),
                new Anime(3L, "Death Note"),
                new Anime(4L, "Naruto"),
                new Anime(5L, "One Piece"));
    }
}
