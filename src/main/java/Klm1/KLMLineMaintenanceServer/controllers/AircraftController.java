package Klm1.KLMLineMaintenanceServer.controllers;

import Klm1.KLMLineMaintenanceServer.models.Aircraft;
import Klm1.KLMLineMaintenanceServer.repositories.AircraftRepositoryJpa;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircraft")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepositoryJpa;


    @GetMapping("/")
    public List<Aircraft> getAircraftList() {
        return (List<Aircraft>) aircraftRepositoryJpa.findAll();
    }

    @GetMapping("/{id}")
    public Aircraft getAircraft(@PathVariable int id) {
        return aircraftRepositoryJpa.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity postAircraft(@RequestBody Aircraft aircraft) {
        try {
            aircraftRepositoryJpa.save(aircraft);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong posting Aircraft with id: " + aircraft.getId());
        }
    }
}
