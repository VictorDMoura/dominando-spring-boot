package academy.devdojo.controller;

import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import academy.devdojo.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping({"v1/animes", "v1/animes/"})
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeMapper mapper;

    private final AnimeService animeService;


    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> list(@RequestParam(required = false) String name) {
        log.info("Request received to list all animes, param name '{}'", name);
        var animes = animeService.findAll(name);
        List<AnimeGetResponse> response = mapper.toAnimeGetResponseList(animes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable long id) {
        log.info("Request received findById: " + id);
        var animeFound = animeService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
        return ResponseEntity.ok(mapper.toAnimeGetResponse(animeFound));
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.info("Request received save: " + request);
        var anime = mapper.toAnime(request);
        anime = animeService.save(anime);
        var response = mapper.toAnimePostResponse(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        log.info("Request received to delete anime with id '{}'", id);
        animeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequest request) {
        log.info("Request received to update anime with '{}'", request);
        var anime = mapper.toAnime(request);
        animeService.update(anime);
        return ResponseEntity.noContent().build();


    }
}
