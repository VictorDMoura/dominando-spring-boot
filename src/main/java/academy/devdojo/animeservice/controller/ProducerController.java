package academy.devdojo.animeservice.controller;

import academy.devdojo.animeservice.domain.Producer;
import academy.devdojo.animeservice.mapper.ProducerMapper;
import academy.devdojo.animeservice.request.ProducerPostRequest;
import academy.devdojo.animeservice.request.ProducerPutRequest;
import academy.devdojo.animeservice.response.ProducerGetResponse;
import academy.devdojo.animeservice.response.ProducerPostResponse;
import academy.devdojo.animeservice.service.ProducerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping({"v1/producers", "v1/producers/"})
@Log4j2
public class ProducerController {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;
    private ProducerService producerService;

    public ProducerController() {
        this.producerService = new ProducerService();
    }


    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> list(@RequestParam(required = false) String name) {
        log.info("Request received to list all producers, param name '{}'", name);
        var producers = producerService.listAll(name);

        var response = MAPPER.toProducerGetResponses(producers);

        return ResponseEntity.ok(response);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-version=v1")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {

        var producer = MAPPER.toProducer(request);
        producer = producerService.save(producer);
        var response = MAPPER.toProducerPostResponse(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Request received to delete the producer by id '{}'", id);

        producerService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.info("Request received to update the producer '{}'", request);

        var producerUpdated = MAPPER.toProducer(request);

        producerService.update(producerUpdated);

        return ResponseEntity.noContent().build();
    }
}
