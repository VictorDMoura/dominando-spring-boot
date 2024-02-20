package academy.devdojo.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProducerGetResponse {
    private Long id;
    private String name;
}
