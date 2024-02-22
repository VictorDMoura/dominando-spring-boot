package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class AnimeHardCodedRepository {


    private static final List<Anime> ANIMES = new ArrayList<>();

    static {
        var anime1 = Anime.builder().id(1L).name("Naruto").createdAt(LocalDateTime.now()).build();
        var anime2 = Anime.builder().id(2L).name("Dragon Ball").createdAt(LocalDateTime.now()).build();
        var anime3 = Anime.builder().id(3L).name("Bleach").createdAt(LocalDateTime.now()).build();
        ANIMES.addAll(List.of(anime1, anime2, anime3));
    }

    public List<Anime> findAll() {
        return ANIMES;
    }

    public Optional<Anime> findById(Long id) {
        return ANIMES.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public List<Anime> findByName(String name) {
        return name == null ? findAll() :
                ANIMES.stream()
                        .filter(a -> a.getName().equalsIgnoreCase(name))
                        .toList();
    }

    public Anime save(Anime anime) {
        ANIMES.add(anime);
        return anime;
    }

    public void deleteById(Long id) {
        ANIMES.removeIf(a -> a.getId().equals(id));
    }

    public void update(Anime anime) {
        deleteById(anime.getId());
        save(anime);
    }


}
