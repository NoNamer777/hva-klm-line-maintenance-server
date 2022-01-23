package Klm1.KLMLineMaintenanceServer.controllers;

import Klm1.KLMLineMaintenanceServer.models.Equipment;
import Klm1.KLMLineMaintenanceServer.models.helper.EquipmentComparator;
import Klm1.KLMLineMaintenanceServer.repositories.EquipmentRepositoryJpa;
import javassist.NotFoundException;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

  @Autowired
  private EquipmentRepositoryJpa equipmentRepository;

  @GetMapping("/")
  public List<Equipment> getEquipments(){
    List<Equipment> equipmentList = equipmentRepository.findAll();
    equipmentList.sort(new EquipmentComparator());

    return equipmentList;
  }

  @GetMapping("/{serialNumber}")
  public Equipment getEquipment(@PathVariable String serialNumber){
    System.out.println(equipmentRepository.findById(serialNumber));
    return equipmentRepository.findById(serialNumber);
  }

  @PostMapping("/")
  public ResponseEntity postEquipment(@RequestBody Equipment equipment){
    try {
      equipmentRepository.save(equipment);
      return ResponseEntity.status(HttpStatus.CREATED).body("Equipment with id:" + equipment.getId() + " was created");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PutMapping("/{serialNumber}")
  public ResponseEntity updateEquipment(@PathVariable String serialNumber, @RequestBody Equipment equipment){
    Equipment existingEquipment = equipmentRepository.findById(serialNumber);
    if (existingEquipment != null) {
      equipmentRepository.save(equipment);
      return ResponseEntity.status(HttpStatus.OK).body("Equipment with id:" + serialNumber + " was updated");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment with id:" + serialNumber + " could not be found");
    }
  }

  @DeleteMapping("/{serialNumber}")
  public String deleteEquipment(@PathVariable String serialNumber){
    equipmentRepository.deleteById(serialNumber);
    return "Delete is successfull";
  }

  @GetMapping("/byType")
  public List<Equipment> getEquipmentByType(@RequestParam(name = "type") String type) {
    return equipmentRepository.findRequestsByType(type);
  }

  @GetMapping("/{typeId}/by")
  public List<Equipment> getEquipmentByTypeAndStatus(@PathVariable int typeId, @RequestParam(name = "status") Equipment.Status status) {
    return equipmentRepository.findRequestsByTypeAndStatus(typeId, status);
  }

  @PutMapping("/{serialNumber}/newStatus")
  public ResponseEntity setEquipmentStatusById(@PathVariable String serialNumber, @RequestBody String status) {
    try {
      if (equipmentRepository.findBySerialNumber(serialNumber).getStatus() == Equipment.Status.Inuse) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Equipment is already being used");
      } else {
        equipmentRepository.setEquipmentStatus(serialNumber, Equipment.Status.valueOf(status));
        return ResponseEntity.ok(HttpStatus.OK);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
