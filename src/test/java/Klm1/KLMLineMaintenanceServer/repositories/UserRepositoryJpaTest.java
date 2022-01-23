/**
 * User tests of the UserRepositoryJpa class
 * @Author Raymond Splinter - 500778433 - IS204
 */

package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.models.User;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryJpaTest {
    @Autowired
    private UserRepositoryJpa repository;

    private List<User> users;
    private User user;

    @BeforeEach
    void setUp() {
        if (repository != null) {
            getUsers();
        }

        user = new User("KLM12345", "John", "GE", "12345");
    }

    @AfterEach
    void deleteAddedUser() {
        User toBeDeletedUser = repository.findById(user.getId());

        if (toBeDeletedUser != null) {
            repository.delete(toBeDeletedUser);
        }
    }

    private void getUsers() {
        users = repository.findAll();
    }

    @Test
    void repositoryNotNull() {
        assertNotNull(repository);
    }

    @Test
    void findAll() {
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    void findById() {
        String id = "KLM00001";
        User user = repository.findById("KLM00001");

        assertNotNull(user);

        System.out.printf("The user is %s", user.getName());

        assertEquals(user.getId(), id);
    }

    @Test
    void addUser() {
        repository.save(user);
        getUsers();

        User addedUser = repository.findById(user.getId());

        assertEquals(user.getId(), addedUser.getId());
    }

    @Test
    void addingDuplicateUsers() {
        int size = users.size();
        repository.save(user);
        getUsers();

        List<User> updatedList = repository.findAll();

        repository.save(user);
        assertEquals(size + 1, updatedList.size());
        assertEquals(users, updatedList);
    }

    @Test
    void editUser() {
        repository.save(user);
        getUsers();

        int listSize = users.size();
        String newName = "Jan";
        User editedUser = user;

        assertNotEquals(user.getName(), newName);

        editedUser.setName(newName);
        repository.save(editedUser);

        getUsers();

        User updatedUser = repository.findById(user.getId());

        assertEquals(listSize, users.size());
        assertNotSame(updatedUser, user);
        assertEquals(newName, updatedUser.getName());
    }

    @Test
    void delete() {
        int listSize = users.size();

        repository.save(user);
        getUsers();

        assertEquals(listSize + 1, users.size());
        assertEquals(user, repository.findById(user.getId()));

        repository.delete(user);
        getUsers();

        assertEquals(listSize, users.size());

        assertNull(repository.findById(user.getId()));
    }

    @Test
    void deletingNonExistingUser() {
        int size = users.size();
        repository.delete(user);

        List<User> updatedList = repository.findAll();

        assertEquals(size, updatedList.size());
        assertEquals(users, updatedList);
    }

    @Test
    void findNonExistingUser() {
        User user = repository.findById("1");

        assertNull(user);
    }

    @Test
    void checkNotAddingNonExistingUser() {
        int size = users.size();
        User addedUser = null;

        try {
            addedUser = repository.save(null);
        } catch (NullPointerException e) {
            assertNull(addedUser);
            assertEquals(users.size(), size);
        }

    }
}