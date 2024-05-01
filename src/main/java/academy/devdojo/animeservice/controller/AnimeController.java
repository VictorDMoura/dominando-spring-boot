package academy.devdojo.animeservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"v1/animes", "v1/animes/"})
public class AnimeController {



    @GetMapping
    public List<String> listAll(){
        return animesList();
    }

    private static List<String> animesList() {
        return List.of("Dragon ball", "Naruto", "One Piece");
    }


}
