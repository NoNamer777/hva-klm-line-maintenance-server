package Klm1.KLMLineMaintenanceServer.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Aircraft Model
 *
 * @author Oscar Wellner, 500806660
 */
class AircraftTest {

    Aircraft aircraft;

    @BeforeEach
    void setUp() {
        aircraft = new Aircraft(
                new ArrayList<>(),
                123456789,
                "Boeing 747 - 800",
                Aircraft.Type.Narrow_body,
                "Boeing"
        );
    }

    @Test
    void getId() {
        int expected = 123456789;
        int actual = aircraft.getId();

        assertEquals(expected, actual);
    }

    @Test
    void setId() {
        int expected = 987654321;

        aircraft.setId(987654321);
        int actual = aircraft.getId();

        assertEquals(expected, actual);
    }

    @Test
    void getName() {
        String expected = "Boeing 747 - 800";
        String actual = aircraft.getName();

        assertEquals(expected, actual);
    }

    @Test
    void setName() {
        String expected = "Boeing 777 - 400";

        aircraft.setName("Boeing 777 - 400");
        String actual = aircraft.getName();

        assertEquals(expected, actual);
    }

    @Test
    void getType() {
        Aircraft.Type expected = Aircraft.Type.Narrow_body;
        Aircraft.Type actual = aircraft.getType();

        assertEquals(expected, actual);
    }

    @Test
    void setType() {
        Aircraft.Type expected = Aircraft.Type.Wide_body;

        aircraft.setType(Aircraft.Type.Wide_body);
        Aircraft.Type actual = aircraft.getType();

        assertEquals(expected, actual);
    }

    @Test
    void getManufacturer() {
        String expected = "Boeing";
        String actual = aircraft.getManufacturer();

        assertEquals(expected, actual);
    }

    @Test
    void setManufacturer() {
        String expected = "Joint Air Striker";

        aircraft.setManufacturer("Joint Air Striker");
        String actual = aircraft.getManufacturer();

        assertEquals(expected, actual);
    }

    @Test
    void getRequests() {
        String expected = "[]";
        String actual = aircraft.getRequests().toString();

        assertEquals(expected, actual);
    }

    @Test
    void setRequests() {
        String expected = "[Request{ id='null', status=OP, equipmentType=Tire Carts, aircraft=Boeing 747 - 800, location=A500, equipment=Equipment{, serialNumber='1234', id='TC-500', type=Tire Carts, status=Usable, statusDescr='null', latitude=0.0, longitude=0.0}, timeStamp=Mon Dec 03 11:15:30 CET 2007, departure=Mon Dec 03 11:15:30 CET 2007, amountOfTires=3}]";

        EquipmentType equipmentType = new EquipmentType(
                new ArrayList<>(),
                new ArrayList<>(),
                123456789,
                "Tire Carts",
                "Small Tire Carts",
                "TC"
        );

        Location location = new Location(
                new ArrayList<>(),
                "A500",
                Location.Type.Buffer
        );

        Equipment equipment = new Equipment(
                new ArrayList<>(),
                "1234",
                "TC-500",
                equipmentType,
                Equipment.Status.Usable,
                null
        );

        List<Request> requests = new ArrayList<>();
        Request request = new Request(
                Request.Status.OP,
                equipmentType,
                aircraft,
                location,
                equipment,
                Date.from(Instant.parse("2007-12-03T10:15:30.00Z")),
                "3"
        );
        request.setTimeStamp(Date.from(Instant.parse("2007-12-03T10:15:30.00Z")));

        requests.add(request);
        aircraft.setRequests(requests);

        String actual = aircraft.getRequests().toString();
        assertEquals(expected, actual);
    }
}