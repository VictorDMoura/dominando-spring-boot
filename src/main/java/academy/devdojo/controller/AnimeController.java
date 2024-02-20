package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"v1/animes", "v1/animes/"})
@Log4j2
public class AnimeController {

    List<Anime> animes = new ArrayList<>(Anime.list());
    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> list() {
        log.info("Request received list");
        List<AnimeGetResponse> animeList = MAPPER.toAnimeGetResponseList(animes);
        return ResponseEntity.ok(animeList);
    }

    @GetMapping("filter")
    public ResponseEntity<AnimeGetResponse> findByName(@RequestParam(defaultValue = "") String name) {
        log.info("Request received findByName: " + name);
        Anime animeFound = animes.stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
        return ResponseEntity.ok(MAPPER.toAnimeGetResponse(animeFound));
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable long id) {
        log.info("Request received findById: " + id);

        Anime animeFound = animes.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
        return ResponseEntity.ok(MAPPER.toAnimeGetResponse(animeFound));
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.info("Request received save: " + request);
        var anime = MAPPER.toAnime(request);
        var response = MAPPER.toAnimePostResponse(anime);

        animes.add(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        log.info("Request received to delete anime with id '{}'", id);
        var animeFound = animes.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found to be deleted"));
        animes.remove(animeFound);
        return ResponseEntity.noContent().build();
    }
}
