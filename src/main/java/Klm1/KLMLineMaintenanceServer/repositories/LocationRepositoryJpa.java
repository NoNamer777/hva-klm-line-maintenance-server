package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.models.Location;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.LocationRepository;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class LocationRepositoryJpa implements LocationRepository {


    @Autowired
    EntityManager entityManager;

    public List<Location> findAll() {
        TypedQuery<Location> query = entityManager.createQuery("select l from Location l", Location.class);

        return query.getResultList();
    }

    public Location findById(String location) {
        return entityManager.find(Location.class, location);
    }

    public void save(Location location){
        entityManager.merge(location);
    }

    public void delete(Location location){
        Location toRemove= entityManager.merge(location);
        entityManager.remove(toRemove);
    }
}
