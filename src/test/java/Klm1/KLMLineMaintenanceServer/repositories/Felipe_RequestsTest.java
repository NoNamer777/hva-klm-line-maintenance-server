package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.models.*;
import Klm1.KLMLineMaintenanceServer.models.helper.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/*
    Author: Felipe da Cruz Gabriel
 */

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Felipe_RequestsTest {

    @Autowired
    private UserRequestRepositoryJpa userRequestRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private RequestRepositoryJpa requestRepositoryJpa;

    private List<User> users;
    private List<Request> requests;
    private List<Equipment> equipmentList;
    private User groundEngineer;
    private User runner;
    private EquipmentType equipmentType;
    private Aircraft aircraft;
    private Location location;
    private Equipment equipment;
    private Request request;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        requests = new ArrayList<>();
        equipmentList = new ArrayList<>();

        groundEngineer = new User(null, "KLM75000", "Mike", "GE", "12345", "OFF");
        runner = new User(null, "KLM00075", "Bob", "RUN", "12345", "OFF");

        equipmentType = new EquipmentType(null,null, 1, "Nitrogen cart", "Nitrogen cart", "NC");
        aircraft = new Aircraft(null, 1, "737-443", Aircraft.Type.Narrow_body, "Boeing");
        location = new Location(null, "A31", Location.Type.Pier);
        equipment = new Equipment(null, "116195", "STOFZ-95", equipmentType, Equipment.Status.Usable, null);
        Request req = new Request();

        req.setAircraft(aircraft);
        req.setEquipmentType(equipmentType);
        req.setStatus(Request.Status.OP);
        req.setLocation(location);
        req.setDeparture(new Date());

        users.add(groundEngineer);
        equipmentList.add(equipment);

        request = requestRepositoryJpa.save(req, "KLM00002");
    }

    //  Checks if a request can be retrieved bij its id
    @Test
    void findById() {
        Request savedRequest = requestRepositoryJpa.save(request, groundEngineer.getId());
        Request r = requestRepositoryJpa.findById(savedRequest.getId());

        assertEquals(savedRequest.getId(), r.getId());
    }

    //  Tests if the requests returned only returns the provided status
    @Test
    void findRequestsByStatus() {
        List<Request> requestList = requestRepositoryJpa.findRequestsByStatus(Request.Status.IP);
        int countInProgress = (int) requestList.stream().map(Request::getStatus).map(status -> status == Request.Status.IP).count();

        assertThat(countInProgress, equalTo(requestList.size()));
    }

    //  Tests if setting a requests status works
    @Test
    void setRequestStatus() {
        Request.Status status = request.getStatus();
        requestRepositoryJpa.setRequestStatus(request.getId(), Request.Status.CAN);
        assertNotEquals(status, requestRepositoryJpa.findById(request.getId()).getStatus());
    }

    //  The method close request delivery should set the status of the request to Closed when run with success
    @Test
    void closeRequestDelivery() {
        requestRepositoryJpa.setEquipmentForRequest(request, equipment);
        requestRepositoryJpa.closeRequestDelivery(request.getId());
        assertThat(requestRepositoryJpa.findById(request.getId()).getStatus(), equalTo(Request.Status.CL));
    }

    //  Checks if the selected equipment successfully has been set to a request
    @Test
    void setEquipmentForRequest() {
        requestRepositoryJpa.setEquipmentForRequest(request, equipment);
        Request requestChangedEquipment = requestRepositoryJpa.findById(request.getId());
        assertThat(equipment.getSerialNumber(), equalTo(requestChangedEquipment.getEquipment().getSerialNumber()));
    }

    //  Checks if the request accepted by a runner is successfully added to his runs
    @Test
    void addRunnerToRequest() {
        userRepositoryJpa.save(runner);
        requestRepositoryJpa.setEquipmentForRequest(request, equipment);
        requestRepositoryJpa.addRunnerToRequest(request, runner.getId());
        List<Request> requestList = userRequestRepositoryJpa.findRequestsByUser(runner.getId());
        boolean isAddedToRequest = requestList.stream().map(Request::getId).anyMatch(id -> id.equals(request.getId()));
        assertTrue(isAddedToRequest);
    }

    //  Check if the equipment that has been selected to a run is set to inuse
    @Test
    void equipmentIsUnusableWhileInRequest() {
        requestRepositoryJpa.setEquipmentForRequest(request, equipment);
        requestRepositoryJpa.addRunnerToRequest(request, "KLM00001");
        requestRepositoryJpa.closeRequestDelivery(request.getId());
        Equipment equipment = requestRepositoryJpa.findById(request.getId()).getEquipment();

        assertEquals(Equipment.Status.Inuse, equipment.getStatus());
    }

    //  Tries to add a request to a non existing user resulting in a exception
    @Test
    void tryToAddRequestToNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> {
            requestRepositoryJpa.save(request, "I_DONT_EXIST");
        });
    }

    //  Checks if the request added tho the user is actually added
    @Test
    void userRequestsSaved() {
        userRepositoryJpa.save(groundEngineer);
        List<Request> beforeRequestList = userRequestRepositoryJpa.findRequestsByUser(groundEngineer.getId());
        int userRequestsSizeBefore =  beforeRequestList.size();

        requestRepositoryJpa.save(request, groundEngineer.getId());
        List<Request> afterRequestList = userRequestRepositoryJpa.findRequestsByUser(groundEngineer.getId());
        int userRequestsSizeAfter = afterRequestList.size();

        assertThat(userRequestsSizeBefore + 1, equalTo(userRequestsSizeAfter));
    }

    //  Mocks the request repository and returns requests that are in progress by user 
    @Test
    void returnInProgressRequestByUser() {
        request.setStatus(Request.Status.IP);
        userRequestRepositoryJpa = Mockito.mock(UserRequestRepositoryJpa.class);
        Mockito.when(userRequestRepositoryJpa.findInProgressRequestsByUser(groundEngineer.getId())).thenReturn(Collections.singletonList(request));
        List<Request> requestList = userRequestRepositoryJpa.findInProgressRequestsByUser(groundEngineer.getId());
        boolean isInProgressRequestList = requestList.stream().map(Request::getStatus).allMatch(status -> status.equals(Request.Status.IP));
        assertTrue(isInProgressRequestList);
    }
}
