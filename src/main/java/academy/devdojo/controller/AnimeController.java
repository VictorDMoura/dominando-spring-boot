package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping({"v1/animes", "v1/animes/"})
@Log4j2
public class AnimeController {

    List<Anime> animes = new ArrayList<>(Anime.list());

    @GetMapping
    public List<Anime> list() {
        log.info("Request received list");
        return animes;
    }

    @GetMapping("filter")
    public Anime findByName(@RequestParam(defaultValue = "") String name) {
        log.info("Request received findByName: " + name);
        return animes.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("{id}")
    public Anime findById(@PathVariable long id) {
        log.info("Request received findById: " + id);
        return animes.stream()
                .filter(anime -> anime.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Anime save(@RequestBody Anime anime) {
        log.info("Request received save: " + anime.getName());
        anime.setId(ThreadLocalRandom.current().nextLong(6, 1000000));
        animes.add(anime);
        return anime;
    }
}
