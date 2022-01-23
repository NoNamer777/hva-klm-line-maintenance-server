package Klm1.KLMLineMaintenanceServer.controllers;

import Klm1.KLMLineMaintenanceServer.KlmLineMaintenanceServerApplication;
import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.repositories.UserRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for UserController
 * @author Yassine el Aatiaoui, 500767860
 * Test rest endpoint UserController
 */
@SpringBootTest(classes = KlmLineMaintenanceServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserController_YASSINE_test {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    // test rest point get All Users
    @Test
    void geAllUsers() {

            ResponseEntity<User[]> response = rest.getForEntity(
                    "http://localhost:8080/users",
                    User[].class);
            // check if HTTP request is ok
            assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // here I'am testing if can i update a user works with the given UserId
    @Test
    void updateUser() {
        User user = userRepositoryJpa.findById("KLM75000");
        user.setPassword("123456");
        userRepositoryJpa.save(user);
        String message = this.rest.getForObject("/users/KLM75000" , String.class);
        assertTrue( message.contains("123456"));
    }
    // here i test if the rest point delete works , i expect that the HTTP response is an error 500 and that is a error in the SERVER_ERROR after i delete User with id KLM000421
    @Test
    void deleteTag() {
        this.rest.delete("/users/KLM000421");
        ResponseEntity<User> responseEntity = this.rest.getForEntity("/users/KLM000421", User.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR , responseEntity.getStatusCode());
    }

}
