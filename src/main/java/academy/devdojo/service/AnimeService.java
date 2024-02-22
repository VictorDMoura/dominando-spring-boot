package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeHardCodedRepository animeHardCodedRepository;


    public List<Anime> findAll(String name){
        return animeHardCodedRepository.findByName(name);
    }

    public Anime save(Anime anime){
        return animeHardCodedRepository.save(anime);
    }

    public Optional<Anime> findById(Long id){
        return animeHardCodedRepository.findById(id);
    }

    public void deleteById(Long id){
        var anime = animeHardCodedRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Anime not found to be deleted"));
        animeHardCodedRepository.deleteById(anime.getId());
    }

    public void update(Anime animeToUpdate){
        var anime = animeHardCodedRepository
                .findById(animeToUpdate.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Anime not found to be updated"));
        animeToUpdate.setCreatedAt(anime.getCreatedAt());
        animeHardCodedRepository.update(animeToUpdate);
    }
}
