package academy.devdojo.animeservice.mapper;

import academy.devdojo.animeservice.domain.Anime;
import academy.devdojo.animeservice.request.AnimePostRequest;
import academy.devdojo.animeservice.request.AnimePutRequest;
import academy.devdojo.animeservice.response.AnimeGetResponse;
import academy.devdojo.animeservice.response.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimeMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Anime toAnime(AnimePostRequest request);

    Anime toAnime(AnimePutRequest request);

    AnimePostResponse toAnimePostResponse(Anime anime);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    List<AnimeGetResponse> toAnimeGetResponses(List<Anime> animes);
}
