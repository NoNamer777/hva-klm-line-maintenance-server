package Klm1.KLMLineMaintenanceServer.controllers;

import Klm1.KLMLineMaintenanceServer.models.EquipmentType;
import Klm1.KLMLineMaintenanceServer.repositories.EquipmentTypeRepositoryJpa;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.EquipmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EquipmentTypeController {

    @Autowired
    private EquipmentTypeRepository equipmentTypeRepositoryJpa;


    @GetMapping("/equipmentType/")
    public List<EquipmentType> getType() {
        System.out.println(equipmentTypeRepositoryJpa.findAll());
        return (List<EquipmentType>) equipmentTypeRepositoryJpa.findAll();
    }

    @PostMapping(value = "/equipmentType/")
    public String postType(@RequestBody EquipmentType equipmentType) {

        equipmentTypeRepositoryJpa.save(equipmentType);

        return "saved succesfully";
    }

    @PutMapping(value = "/equipmentType/")
    public String updateType(@RequestBody EquipmentType equipmentType) {

        equipmentTypeRepositoryJpa.save(equipmentType);

        return "updated successfully";

    }

    @DeleteMapping(value = "/equipmentType/{id}")
    public String deleteType(@PathVariable int id) {

        equipmentTypeRepositoryJpa.deleteById(id);

        return "Delete is successfull";
    }

}
