package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryJpa implements UserRepository{

    @Autowired
    private EntityManager em;

    @Override
    public List<User> findAll(){
        TypedQuery<User> query= em.createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    @Override
    public void delete(User user){
        User toRemove = em.merge(user);
        em.remove(toRemove);
    }

    @Override
    public User findById(String id) {
        return em.find(User.class, id);
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) em.persist(user);
        else em.merge(user);

        return user;
    }
}
