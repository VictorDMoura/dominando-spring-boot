package academy.devdojo.animeservice.mapper;

import academy.devdojo.animeservice.domain.Producer;
import academy.devdojo.animeservice.request.ProducerPostRequest;
import academy.devdojo.animeservice.request.ProducerPutRequest;
import academy.devdojo.animeservice.response.ProducerGetResponse;
import academy.devdojo.animeservice.response.ProducerPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProducerMapper {

    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Producer toProducer(ProducerPostRequest request);

    ProducerPostResponse toProducerPostResponse(Producer producer);

    ProducerGetResponse toProducerGetResponse(Producer producer);

    List<ProducerGetResponse> toProducerGetResponses(List<Producer> producers);

    @Mapping(source = "createdAt", target = "createdAt")
    Producer toProducer(ProducerPutRequest request, LocalDateTime createdAt);
}
