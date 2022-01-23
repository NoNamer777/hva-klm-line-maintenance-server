package Klm1.KLMLineMaintenanceServer.repositories.interfaces;

import Klm1.KLMLineMaintenanceServer.models.Equipment;


import java.util.List;

public interface EquipmentRepository {


    List<Equipment> findAll();

    Equipment save(Equipment equipment);

    void deleteById(String id);

    Equipment findById(String id);
}
