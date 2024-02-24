package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class AnimeHardCodedRepository {

    private final AnimeData animeData;


    public List<Anime> findAll() {
        return animeData.getAnimes();
    }

    public Optional<Anime> findById(Long id) {
        return animeData.getAnimes().stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public List<Anime> findByName(String name) {
        return name == null ? findAll() :
                animeData.getAnimes().stream()
                        .filter(a -> a.getName().equalsIgnoreCase(name))
                        .toList();
    }

    public Anime save(Anime anime) {
        animeData.getAnimes().add(anime);
        return anime;
    }

    public void deleteById(Long id) {
        animeData.getAnimes().removeIf(a -> a.getId().equals(id));
    }

    public void update(Anime anime) {
        deleteById(anime.getId());
        save(anime);
    }


}
