package academy.devdojo.domain;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private LocalDateTime createdAt;

}
