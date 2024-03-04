package academy.devdojo.controller;


import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerData;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(ProducerController.class)
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProducerData producerData;
    @SpyBean
    private ProducerHardCodedRepository repository;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();

        List<Producer> producers = new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }


    @Test
    @DisplayName("findAll() returns a list and when name is null status code 200 when successful")
    void list_ReturnsStatusCode200_WhenSuccessful() throws Exception {
        var response = readResourceFile("producer/get-producer-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findAll() returns a list and when find producer status code 200 when successful")
    void list_ReturnsStatusCode200_WhenSuccessfulFindProducer() throws Exception {
        var response = readResourceFile("producer/get-producer-Ufotable-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers").param("name", "Ufotable"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findAll() return a empty list when name not found and status code 200")
    void list_ReturnsEmptyList_WhenNameNotFound() throws Exception {
        var response = readResourceFile("producer/get-producer-not-found-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers").param("name", "xxx"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("save() returns status code 201 when successful")
    void save_ReturnsStatusCode201_WhenSuccessful() throws Exception {
        var request = readResourceFile("producer/post-request-producer-200.json");
        var response = readResourceFile("producer/post-response-producer-201.json");
        var producerToBeSaved = Producer.builder()
                .id(99L).name("MAPPA")
                .createdAt(LocalDateTime.now())
                .build();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToBeSaved);


        mockMvc.perform(MockMvcRequestBuilders.post("/v1/producers")
                        .contentType("application/json")
                        .content(request)
                        .header("X-API-VERSION", "v1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("update() returns status code 204 when successful")
    void deleteById_ReturnsStatusCode204_WhenSuccessful() throws Exception {
        var request = readResourceFile("producer/put-request-producer-200.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/producers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("update() throws exception when no producer is found")
    void update_ThrowsException_WhenProducerNotFound() throws Exception {
        var request = readResourceFile("producer/put-request-producer-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/producers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Producer not found to be updated"));
    }

    @Test
    @DisplayName("deleteById() returns status code 204 when successful")
    void update_ReturnsStatusCode204_WhenSuccessful() throws Exception {
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/producers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("deleteById() throws exception when no producer is found")
    void deleteById_ThrowsException_WhenProducerNotFound() throws Exception {
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/producers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Producer not found to be deleted"));
    }


    private String readResourceFile(String fileName) throws Exception {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName))
                .getFile();

        return new String(Files.readAllBytes(file.toPath()));
    }

}