package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.repository.AnimeHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeHardCodedRepository animeHardCodedRepository;

    public List<Anime> listAll(String name) {
        return animeHardCodedRepository.findByName(name);
    }

    public Anime findById(Long id) {
        return animeHardCodedRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anime not found"));
    }

    public Anime save(Anime anime) {
        return animeHardCodedRepository.save(anime);
    }

    public void deleteById(Long id) {
        var anime = findById(id);
        animeHardCodedRepository.delete(anime);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate);
        animeHardCodedRepository.update(animeToUpdate);
    }

    private void assertAnimeExists(Anime animeToUpdate) {
        findById(animeToUpdate.getId());
    }
}
