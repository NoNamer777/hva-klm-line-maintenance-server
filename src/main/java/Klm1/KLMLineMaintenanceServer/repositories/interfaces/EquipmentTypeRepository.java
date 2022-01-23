package Klm1.KLMLineMaintenanceServer.repositories.interfaces;

import Klm1.KLMLineMaintenanceServer.models.Equipment;
import Klm1.KLMLineMaintenanceServer.models.EquipmentType;

import java.util.List;

public interface EquipmentTypeRepository {
    List<EquipmentType> findAll();

    void save(EquipmentType equipmentType);

    void deleteById(int id);

    EquipmentType findById(int id);
}
