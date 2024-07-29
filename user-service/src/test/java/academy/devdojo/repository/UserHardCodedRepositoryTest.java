package academy.devdojo.repository;

import academy.devdojo.commons.UserUtils;
import academy.devdojo.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserHardCodedRepositoryTest {

    @InjectMocks
    private UserHardCodedRepository repository;
    @InjectMocks
    private UserUtils userUtils;
    @Mock
    private UserData userData;

    private List<User> users;

    @BeforeEach
    void init() {
        users = userUtils.newUserList();
        BDDMockito.when(userData.getUsers()).thenReturn(users);
    }


    @Test
    @DisplayName("findAll() returns a list with all users")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        var users = repository.findAll();
        Assertions.assertThat(users).hasSameElementsAs(this.users);
    }


}