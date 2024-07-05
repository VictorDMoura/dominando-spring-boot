package academy.devdojo.animeservice.service;

import academy.devdojo.animeservice.domain.Anime;
import academy.devdojo.animeservice.repository.AnimeHardCodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class AnimeService {

    private AnimeHardCodedRepository animeHardCodedRepository;

    public AnimeService() {
        this.animeHardCodedRepository = new AnimeHardCodedRepository();
    }

    public List<Anime> listAll(String name) {
        return animeHardCodedRepository.findByName(name);
    }

    public Anime findById(Long id) {
        return animeHardCodedRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public Anime save(Anime anime) {
        return animeHardCodedRepository.save(anime);
    }

    public void deleteById(Long id) {
        var anime = findById(id);
        animeHardCodedRepository.delete(anime);
    }

    public void update(Anime animeToUpdate){
        assertAnimeExists(animeToUpdate);
        animeHardCodedRepository.update(animeToUpdate);
    }

    private void assertAnimeExists(Anime animeToUpdate) {
        findById(animeToUpdate.getId());
    }
}
