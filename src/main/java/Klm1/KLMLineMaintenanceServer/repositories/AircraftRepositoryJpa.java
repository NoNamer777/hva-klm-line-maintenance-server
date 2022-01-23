package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.models.Aircraft;
import Klm1.KLMLineMaintenanceServer.models.EquipmentType;
import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AircraftRepositoryJpa implements AircraftRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Aircraft> findAll() {
        TypedQuery<Aircraft> query = em.createQuery("select a from Aircraft a", Aircraft.class);
        return query.getResultList();
    }

    @Override
    public Aircraft save(Aircraft aircraft) {
        return em.merge(aircraft);

    }

    @Override
    public void delete(Aircraft aircraft) {
        Aircraft toRemove = em.merge(aircraft);
        em.remove(toRemove);
    }

    @Override
    public Aircraft findById(int id) {
        return em.find(Aircraft.class, id);

    }


}
