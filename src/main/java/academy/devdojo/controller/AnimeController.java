package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"v1/animes", "v1/animes/"})
@Log4j2
public class AnimeController {


    @GetMapping
    public List<Anime> list() {
        log.info("Request received list");
        return Anime.list();
    }

    @GetMapping("filter")
    public Anime findByName(@RequestParam(defaultValue = "") String name) {
        log.info("Request received findByName: " + name);
        return Anime.list().stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("{id}")
    public Anime findById(@PathVariable long id) {
        log.info("Request received findById: " + id);
        return Anime.list().stream()
                .filter(anime -> anime.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
