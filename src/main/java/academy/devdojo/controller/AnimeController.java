package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"v1/animes", "v1/animes/"})
public class AnimeController {


    @GetMapping
    public List<Anime> list() {
        return Anime.list();
    }

    @GetMapping("filter")
    public Anime findByName(@RequestParam(defaultValue = "") String name) {
        return Anime.list().stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("{id}")
    public Anime findById(@PathVariable long id) {
        return Anime.list().stream()
                .filter(anime -> anime.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
