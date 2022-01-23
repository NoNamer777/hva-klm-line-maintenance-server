package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.models.Equipment;
import Klm1.KLMLineMaintenanceServer.models.EquipmentType;
import Klm1.KLMLineMaintenanceServer.models.Request;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class EquipmentRepositoryJpa implements EquipmentRepository {
    @Autowired
    private EntityManager em;

    //  Get all
    @Override
    public List<Equipment> findAll() {
        TypedQuery<Equipment> namedQuery = em.createNamedQuery("find_all_equipment", Equipment.class);
        return namedQuery.getResultList();
    }

    //  Get by id
    @Override
    public Equipment findById(String id) {
        return em.find(Equipment.class, id);
    }

    public Equipment findBySerialNumber(String serialNumber) {
        return em.find(Equipment.class, serialNumber);
    }

    //  Post
    @Override
    public Equipment save(Equipment equipment) {
        return em.merge(equipment);
    }

    //  Change equipment status
    public Equipment setEquipmentStatus(String serialNumber, Equipment.Status status) {
        Equipment equipment = em.find(Equipment.class, serialNumber);
        System.out.println("=====================");
        System.out.println(equipment);
        equipment.setStatus(status);
        return em.merge(equipment);
    }

    //  Delete equipment
    @Override
    public void deleteById(String id) {
        Equipment equipment = em.find(Equipment.class, id);
        em.remove(equipment);
    }

    public List<Equipment> findRequestsByType(String type) {
        TypedQuery<Equipment> namedQuery = em.createNamedQuery("find_equipment_by_type", Equipment.class);
        return namedQuery.setParameter("type", type).getResultList();
    }

    public List<Equipment> findRequestsByTypeAndStatus(int typeId, Equipment.Status status) {
        EquipmentType equipmentType = em.find(EquipmentType.class, typeId);
        TypedQuery<Equipment> namedQuery = em.createNamedQuery("find_equipment_by_type_and_status", Equipment.class);
        return namedQuery.setParameter("type", equipmentType).setParameter("status", status).getResultList();
    }
}
