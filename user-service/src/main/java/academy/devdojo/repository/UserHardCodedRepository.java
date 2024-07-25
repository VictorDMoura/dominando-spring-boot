package academy.devdojo.repository;

import academy.devdojo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserHardCodedRepository {

    private final UserData userData;

    public List<User> findAll() {
        return userData.getUsers();
    }
}
