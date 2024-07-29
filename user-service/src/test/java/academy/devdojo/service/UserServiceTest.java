package academy.devdojo.service;

import academy.devdojo.commons.UserUtils;
import academy.devdojo.domain.User;
import academy.devdojo.repository.UserHardCodedRepository;
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
class UserServiceTest {

    @InjectMocks
    private UserService service;
    @InjectMocks
    private UserUtils userUtils;
    @Mock
    private UserHardCodedRepository repository;

    private List<User> users;

    @BeforeEach
    void init() {
        users = userUtils.newUserList();
    }

    @Test
    @DisplayName("findAll() returns a list with all users")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(this.users);

        var users = service.findAll();
        Assertions.assertThat(users).hasSameElementsAs(this.users);
    }

}