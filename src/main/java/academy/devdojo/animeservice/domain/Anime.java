package academy.devdojo.animeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Anime {

    private Long id;
    private String name;
    private static List<Anime> animes = new ArrayList<>();

    static {
        var jigokuraku = new Anime(1L, "Jigokuraku");
        var konosuba = new Anime(2L, "Konosuba");
        var drStone = new Anime(3L, "Dr.Stone");
        animes.addAll(List.of(jigokuraku, konosuba, drStone));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}
