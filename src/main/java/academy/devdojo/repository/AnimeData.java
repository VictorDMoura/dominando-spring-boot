package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {

    private final List<Anime> animes = new ArrayList<>();

    {
        var anime1 = Anime.builder().id(1L).name("Naruto").createdAt(LocalDateTime.now()).build();
        var anime2 = Anime.builder().id(2L).name("Dragon Ball").createdAt(LocalDateTime.now()).build();
        var anime3 = Anime.builder().id(3L).name("Bleach").createdAt(LocalDateTime.now()).build();
        animes.addAll(List.of(anime1, anime2, anime3));
    }

    public List<Anime> getAnimes() {
        return animes;
    }
}
