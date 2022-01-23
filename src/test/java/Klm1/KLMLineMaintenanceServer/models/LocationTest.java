package Klm1.KLMLineMaintenanceServer.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the location model
 *
 * @author Oscar Wellner, 500806660
 */
class LocationTest {

    private Location locationA500;
    private Location locationB200;

    @BeforeEach
    void setUp() {
        locationA500 = new Location(new ArrayList<>(), "A-500", Location.Type.Buffer);
        locationB200 = new Location(new ArrayList<>(), "B-200", Location.Type.Pier);
    }

    @Test
    void getLocation() {
        String expectedA500 = "A-500";
        String expectedB200 = "B-200";

        String resultA500 = locationA500.getLocation();
        String resultB200 = locationB200.getLocation();

        assertEquals(expectedA500, resultA500);
        assertEquals(expectedB200, resultB200);
    }

    @Test
    void setLocation() {
        String expectedA500 = "D-600";
        String expectedB200 = "C-300";

        locationA500.setLocation("D-600");
        locationB200.setLocation("C-300");

        String resultA500 = locationA500.getLocation();
        String resultB200 = locationB200.getLocation();

        assertEquals(expectedA500, resultA500);
        assertEquals(expectedB200, resultB200);
    }

    @Test
    void getType() {
        Location.Type expectedA500 = Location.Type.Buffer;
        Location.Type expectedB200 = Location.Type.Pier;

        Location.Type resultA500 = locationA500.getType();
        Location.Type resultB200 = locationB200.getType();

        assertEquals(expectedA500, resultA500);
        assertEquals(expectedB200, resultB200);
    }

    @Test
    void setType() {
        Location.Type expectedA500 = Location.Type.Pier;
        Location.Type expectedB200 = Location.Type.Buffer;

        locationA500.setType(Location.Type.Pier);
        locationB200.setType(Location.Type.Buffer);

        Location.Type resultA500 = locationA500.getType();
        Location.Type resultB200 = locationB200.getType();

        assertEquals(expectedA500, resultA500);
        assertEquals(expectedB200, resultB200);
    }

    @Test
    void getRequests() {
        String expectedA500 = "[]";
        String expectedB200 = "[]";

        String resultA500 = locationA500.getRequests().toString();
        String resultB200 = locationB200.getRequests().toString();

        assertEquals(expectedA500, resultA500);
        assertEquals(expectedB200, resultB200);
    }

    @Test
    void setRequests() {
        String expectedA500 = "[Request{ id='null', status=OP, equipmentType=Tire Carts, aircraft=Boeing 737 - 800, location=A-500, equipment=Equipment{, serialNumber='567891234', id='TC-10000', type=Tire Carts, status=Usable, statusDescr='null', latitude=0.0, longitude=0.0}, timeStamp=Mon Dec 03 11:15:30 CET 2007, departure=Mon Dec 03 11:15:30 CET 2007, amountOfTires=3}]";
        String expectedB200 = "[Request{ id='null', status=OP, equipmentType=Tire Carts, aircraft=Boeing 737 - 800, location=B-200, equipment=Equipment{, serialNumber='567891234', id='TC-10000', type=Tire Carts, status=Usable, statusDescr='null', latitude=0.0, longitude=0.0}, timeStamp=Mon Dec 03 11:15:30 CET 2007, departure=Mon Dec 03 11:15:30 CET 2007, amountOfTires=3}]";

        List<Request> requestsA500 = new ArrayList<>();
        List<Request> requestsB200 = new ArrayList<>();
        EquipmentType equipmentType = new EquipmentType(
                new ArrayList<>(),
                new ArrayList<>(),
                123456789,
                "Tire Carts",
                "Small Tire Carts",
                "TC"
        );

        Equipment equipment = new Equipment(
                new ArrayList<>(),
                "567891234",
                "TC-10000",
                equipmentType,
                Equipment.Status.Usable,
                null
        );

        Aircraft aircraft = new Aircraft(
                new ArrayList<>(),
                987654321,
                "Boeing 737 - 800",
                Aircraft.Type.Wide_body,
                "Boeing"
        );

        Request requestA500 = new Request(
                Request.Status.OP,
                equipmentType,
                aircraft,
                locationA500,
                equipment,
                Date.from(Instant.parse("2007-12-03T10:15:30.00Z")),
                "3"
        );
        requestA500.setTimeStamp(Date.from(Instant.parse("2007-12-03T10:15:30.00Z")));
        requestsA500.add(requestA500);

        Request requestB200 = new Request(
                Request.Status.OP,
                equipmentType,
                aircraft,
                locationB200,
                equipment,
                Date.from(Instant.parse("2007-12-03T10:15:30.00Z")),
                "3"
        );
        requestB200.setTimeStamp(Date.from(Instant.parse("2007-12-03T10:15:30.00Z")));
        requestsB200.add(requestB200);

        locationA500.setRequests(requestsA500);
        locationB200.setRequests(requestsB200);

        String resultA500 = locationA500.getRequests().toString();
        String resultB200 = locationB200.getRequests().toString();

        assertEquals(expectedA500, resultA500);
        assertEquals(expectedB200, resultB200);
    }
}