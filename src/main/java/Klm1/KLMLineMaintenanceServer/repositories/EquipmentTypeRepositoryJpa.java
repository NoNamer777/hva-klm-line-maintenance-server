package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.models.Equipment;
import Klm1.KLMLineMaintenanceServer.models.EquipmentType;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.EquipmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class EquipmentTypeRepositoryJpa implements EquipmentTypeRepository {
    @Autowired
    EntityManager entityManager;

    @Override
    public List<EquipmentType> findAll(){
        TypedQuery<EquipmentType> query = entityManager.createQuery("select et from EquipmentType et", EquipmentType.class);

        return query.getResultList();
    }

    @Override
    public void save(EquipmentType equipmentType){
        entityManager.merge(equipmentType);
    }

    @Override
    public void deleteById(int id){
        EquipmentType equipmentType = entityManager.find(EquipmentType.class, id);
        entityManager.remove(equipmentType);
    }

    @Override
    public EquipmentType findById(int id) {
        return entityManager.find(EquipmentType.class, id);
    }


}
