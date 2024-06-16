package academy.devdojo.animeservice.controller;

import academy.devdojo.animeservice.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(path = {"v1/animes", "v1/animes/"})
@Log4j2
public class AnimeController {

    @GetMapping
    public List<Anime> list(@RequestParam(required = false) String name) {
        var animes = Anime.getAnimes();
        log.info("Request received to list all animes, param name '{}'", name);
        if (name == null) return animes;
        return animes.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();
    }

    @GetMapping("{id}")
    public Anime findById(@PathVariable Long id) {
        log.info("Request received find anime by id '{}'", id);
        return Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseGet(null);
    }

    @PostMapping
    public Anime save(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong());
        Anime.getAnimes().add(anime);
        return anime;
    }
}

