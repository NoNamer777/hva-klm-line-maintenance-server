package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.KlmLineMaintenanceServerApplication;
import Klm1.KLMLineMaintenanceServer.controllers.AuthenticateController;
import Klm1.KLMLineMaintenanceServer.controllers.UserController;
import Klm1.KLMLineMaintenanceServer.models.Aircraft;
import Klm1.KLMLineMaintenanceServer.models.Location;
import Klm1.KLMLineMaintenanceServer.models.Request;
import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.repositories.*;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.beans.binding.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.UnknownServiceException;
import java.time.Duration;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests MockTest
 *
 * @author Yassine el Aatiaoui, 500767860
 * Test rest endpoint User Controller
 */

@SpringBootTest(classes = KlmLineMaintenanceServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestMock_YASSINE_test {

    @Mock
    private UserRepository mockUserRepo;

    //with this test I use mock Test to save the user. Then I get all users
    @Test
    public void testMockMethod() {

        List<User> users = new ArrayList<>();
        User user = new User("KLM00013", "Jone", "RUN", "12345");
        //test save User
        Mockito.when(mockUserRepo.save(user)).thenReturn(user);
        User savedUser = mockUserRepo.save(user);
        mockUserRepo.save(user);
        String result = savedUser.getId();
        assertEquals(result, user.getId());

        // test get All users with Mock
        Mockito.when(mockUserRepo.findAll()).thenReturn(users);
        List<User> getUsers = new ArrayList<>();
        getUsers = mockUserRepo.findAll();
        assertEquals(getUsers, users);

    }
}



