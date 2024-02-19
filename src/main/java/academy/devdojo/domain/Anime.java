package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class Anime {
    private Long id;
    private String name;


    public static List<Anime> list() {
        return List.of(new Anime(1L, "Boku no Hero"),
                new Anime(2L, "Berserk"),
                new Anime(3L, "Death Note"),
                new Anime(4L, "Naruto"),
                new Anime(5L, "One Piece"));
    }
}
