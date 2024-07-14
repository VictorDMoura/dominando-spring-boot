package academy.devdojo.animeservice.repository;


import academy.devdojo.animeservice.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {

    private final List<Anime> anime = new ArrayList<>();

    {
        var jigokuraku = Anime.builder().id(1L).name("Jigokuraku").build();
        var konosuba = Anime.builder().id(2L).name("Konosuba").build();
        var drStone = Anime.builder().id(3L).name("Dr.Stone").build();
        anime.addAll(List.of(jigokuraku, konosuba, drStone));
    }

    public List<Anime> getAnimes() {
        return anime;
    }
}
