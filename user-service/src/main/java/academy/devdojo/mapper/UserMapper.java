package academy.devdojo.mapper;


import academy.devdojo.domain.User;
import academy.devdojo.request.UserPostRequest;
import academy.devdojo.response.UserGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    User toUser(UserPostRequest request);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> toUserGetResponses(List<User> users);
}
