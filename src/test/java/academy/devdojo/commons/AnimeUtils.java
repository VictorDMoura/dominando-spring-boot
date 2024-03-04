package academy.devdojo.commons;

import academy.devdojo.domain.Anime;
import academy.devdojo.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {
    public List<Anime> newAnimeList() {
        var shingekiNoKyojin = Anime.builder().id(1L).name("Shingeki no Kyojin").createdAt(LocalDateTime.now()).build();
        var bokuNoHero = Anime.builder().id(2L).name("Boku no Hero").createdAt(LocalDateTime.now()).build();
        var onePunchMan = Anime.builder().id(3L).name("One Piece").createdAt(LocalDateTime.now()).build();

        return new ArrayList<>(List.of(shingekiNoKyojin, bokuNoHero, onePunchMan));

    }

    public Anime newAnimeToSave() {
        return Anime.builder()
                .id(99L).name("Bleach")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
