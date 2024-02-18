package academy.devdojo.domain;

import java.util.List;

public class Anime {
    private Long id;
    private String name;

    public Anime(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<Anime> list() {
        return List.of(new Anime(1L, "Boku no Hero"),
                new Anime(2L, "Berserk"),
                new Anime(3L, "Death Note"),
                new Anime(4L, "Naruto"),
                new Anime(5L, "One Piece"));
    }
}
