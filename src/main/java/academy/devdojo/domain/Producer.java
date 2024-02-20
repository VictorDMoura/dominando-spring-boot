package academy.devdojo.domain;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private LocalDateTime createAt;

    private static List<Producer> producers = new ArrayList<>();

    static {
        var mappa = Producer.builder().id(1L).name("Mappa").createAt(LocalDateTime.now()).build();
        var kyotoAnimatio = Producer.builder().id(2L).name("Kyoto Animation").createAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(3L).name("Madhouse").createAt(LocalDateTime.now()).build();

        producers.addAll(List.of(mappa, kyotoAnimatio, madhouse));
    }

    public static List<Producer> list() {
        return producers;
    }
}
