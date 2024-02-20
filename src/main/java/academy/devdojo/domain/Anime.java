package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {
    @EqualsAndHashCode.Include
    private Long id;
    @JsonProperty(value = "name")
    private String name;
    private LocalDateTime createdAt;


    private static List<Anime> animes = new ArrayList<>();

    static {
        var anime1 = Anime.builder().id(1L).name("Naruto").createdAt(LocalDateTime.now()).build();
        var anime2 = Anime.builder().id(2L).name("Dragon Ball").createdAt(LocalDateTime.now()).build();
        var anime3 = Anime.builder().id(3L).name("Bleach").createdAt(LocalDateTime.now()).build();
        animes.addAll(List.of(anime1, anime2, anime3));
    }

    public static List<Anime> list() {
        return animes;
    }
}
