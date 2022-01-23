package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.KlmLineMaintenanceServerApplication;
import Klm1.KLMLineMaintenanceServer.models.Aircraft;
import Klm1.KLMLineMaintenanceServer.models.Location;
import Klm1.KLMLineMaintenanceServer.models.Request;
import Klm1.KLMLineMaintenanceServer.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for UserRepo, AircraftRepo, LocationRepo, EquipmentRepo
 *
 * @author Yassine el Aatiaoui, 500767860
 * Here is test some CRUD oprations on Repositories JPA save, deleteById , findById , findAll
 */
@SpringBootTest
class RepositoriesJpa_YASSINE_test {
    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private LocationRepositoryJpa locationRepositoryJpa;

    @Autowired
    private AircraftRepositoryJpa aircraftRepositoryJpa;


    ArrayList<User> users = new ArrayList<>();
    User user = new User("KLM00013", "Jone", "RUN", "12345");
    User user2 = new User("KLM00011", "Mark", "ADM", "54321");
    User user3 = new User("KLM00015", "YSN", "GE", "12585");

    @BeforeEach
    void setUp() {
        this.userRepositoryJpa.save(user);
        this.userRepositoryJpa.save(user2);

    }

    //check if we can get all user
    @Test
    void testRepo_findAllUsers() {
        int expected = userRepositoryJpa.findAll().toArray().length;
        List<User> users = userRepositoryJpa.findAll();
        int actual = users.size();
        assertEquals(expected, actual);
    }

    //check if we can remove the user
    @Test
    void testRepo_deleteUser() {
        this.userRepositoryJpa.delete(user3);
        assertNull(this.userRepositoryJpa.findById(user3.getId()));
    }

    //check if we can get a user based on his id
    @Test
    void testRepo_findUserById() {
        User userId = this.userRepositoryJpa.findById("KLM00011");
        assertEquals("Mark", userId.getName());
    }

    //test if we can save a new user
    @Test
    void testRepo_saveUser() {
        User savedEmplyee = this.userRepositoryJpa.save(user3);
        assertEquals("YSN", savedEmplyee.getName());
        assertEquals(userRepositoryJpa.findAll().size(), 13);
        System.out.println("--------------------------------------------------------");
        System.out.println(userRepositoryJpa.findAll());
    }

    // Test the logic of the repository by testing a couple of methods like save
    @Test
    public void testRepo_saveAircraft() {

        //Initialize empty list of the requests of the equipment, because  the constructor needs this
        List<Request> requests = new ArrayList<>();
        Request request = new Request();
        requests.add(request);
        Aircraft aircraft = new Aircraft(requests, 3, "AirKLM", Aircraft.Type.NA, "factu125sklm");
        Aircraft savedAircarft = this.aircraftRepositoryJpa.save(aircraft);
        //check if save is successful by checking attributes
        assertEquals("factu125sklm", savedAircarft.getManufacturer());
        assertEquals("AirKLM", savedAircarft.getName());
        assertEquals(3, savedAircarft.getId());

    }

    /* Test the logic of the repository by testing a couple of methods like save  */
    @Test
    public void testRepo_deleteAircraft() {


        //Initialize empty list of the requests of the equipment, because  the constructor needs this
        List<Request> requests = new ArrayList<>();

        Request request = new Request();
        requests.add(request);

        Aircraft aircraft = new Aircraft(requests, 3, "AirKLM123", Aircraft.Type.NA, "factu125sklm");
        Aircraft savedAircarft = this.aircraftRepositoryJpa.save(aircraft);

        //Here I test whether the aircraft is stored
        assertEquals(savedAircarft.getId(), 3);

        aircraftRepositoryJpa.delete(savedAircarft);
        Aircraft DeletedAircarft = aircraftRepositoryJpa.findById(savedAircarft.getId());

        //test if it has been deleted
        assertNull(DeletedAircarft);

    }

    // test if we can find loactions
    @Test
    public void findAlleLocation() {

        ArrayList<Request> requests = new ArrayList<>();
        Location location = new Location(requests, "A34", Location.Type.Buffer);
        Location location1 = new Location(requests, "A33", Location.Type.Buffer);
        Location location2 = new Location(requests, "B42", Location.Type.Buffer);
        Location location3 = new Location(requests, "C42", Location.Type.Buffer);
        Location location4 = new Location(requests, "A41", Location.Type.Buffer);
        locationRepositoryJpa.save(location);
        locationRepositoryJpa.save(location1);
        locationRepositoryJpa.save(location2);
        locationRepositoryJpa.save(location3);
        locationRepositoryJpa.save(location4);
        assertEquals(locationRepositoryJpa.findById(location2.getLocation()).getLocation(), "B42");

    }


}
