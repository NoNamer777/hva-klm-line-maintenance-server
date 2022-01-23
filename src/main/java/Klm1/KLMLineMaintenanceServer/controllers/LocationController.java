package Klm1.KLMLineMaintenanceServer.controllers;

import Klm1.KLMLineMaintenanceServer.models.Location;
import Klm1.KLMLineMaintenanceServer.repositories.LocationRepositoryJpa;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationRepository locationRepositoryJpa;


    @GetMapping("/locations")
    public List<Location> getLocation() {
        System.out.println(locationRepositoryJpa.findAll());
        return (List<Location>) locationRepositoryJpa.findAll();
    }

    @GetMapping("/locations/{id}")
    public Location getLocationById(@PathVariable(name = "id") String id){
        return locationRepositoryJpa.findById(id);
    }

    @PostMapping(value = "/locations")
    public String postLocation(@RequestBody Location location) {

        locationRepositoryJpa.save(location);
        return "saved succesfully";
    }

    @PutMapping(value = "/locations")
    public String updateLocation(@RequestBody Location location) {

        locationRepositoryJpa.save(location);

        return "updated successfully";

    }

    @DeleteMapping(value = "/locations/{location}")
    public String deleteLocation(@PathVariable String location) {

        Location location1 = locationRepositoryJpa.findById(location);

        locationRepositoryJpa.delete(location1);

        return "Delete is successfull";
    }

}
