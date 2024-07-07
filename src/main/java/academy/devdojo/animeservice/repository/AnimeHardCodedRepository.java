package academy.devdojo.animeservice.repository;

import academy.devdojo.animeservice.domain.Anime;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimeHardCodedRepository {

    private static final List<Anime> ANIME_LIST = new ArrayList<>();

    static {
        var jigokuraku = Anime.builder().id(1L).name("Jigokuraku").build();
        var konosuba = Anime.builder().id(2L).name("Konosuba").build();
        var drStone = Anime.builder().id(3L).name("Dr.Stone").build();
        ANIME_LIST.addAll(List.of(jigokuraku, konosuba, drStone));
    }

    public List<Anime> listAll() {
        return ANIME_LIST;
    }

    public List<Anime> findByName(String name) {
        return name == null ? ANIME_LIST :
                ANIME_LIST.stream()
                        .filter(anime -> anime.getName().equalsIgnoreCase(name))
                        .toList();
    }

    public Optional<Anime> findById(Long id) {
        return ANIME_LIST.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst();
    }

    public Anime save(Anime anime) {
        ANIME_LIST.add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        ANIME_LIST.remove(anime);
    }

    public void update(Anime anime) {
        ANIME_LIST.remove(anime);
        ANIME_LIST.add(anime);
    }
}
