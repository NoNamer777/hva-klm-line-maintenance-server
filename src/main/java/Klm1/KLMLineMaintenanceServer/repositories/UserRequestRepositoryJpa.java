package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.models.Request;
import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.models.UserRequest;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRequestRepositoryJpa implements UserRequestRepository {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    private User getUserById(String userId) {
        return em.find(User.class, userId);
    }

    @Override
    public List<UserRequest> findAll() {
        TypedQuery<UserRequest> query= em.createQuery("select ur from UserRequest ur", UserRequest.class);

        return query.getResultList();
    }

    public UserRequest findById(String id) {
        return em.find(UserRequest.class, id);
    }

    public UserRequest findByRequest(Request request) {
        TypedQuery<UserRequest> query= em.createNamedQuery("find_user_request_by_request_id", UserRequest.class);
        query.setParameter("request", request);
        return query.getSingleResult();
    }

    public List<Request> findRequestsByUser(String userId) {
        User user = getUserById(userId);
        TypedQuery<Request> query;

        if (user.getRole().equals(User.Role.GE.toString())) {
            query = em.createQuery("select request_id from UserRequest ur where user_id = :user", Request.class);
            query.setParameter("user", user);
        } else if (user.getRole().equals(User.Role.RUN.toString())) {
            query = em.createQuery("select request_id from UserRequest ur where acceptedBy = :user_id OR closedBy = :user_id", Request.class);
            query.setParameter("user_id", user.getId());
        } else {
            query = em.createQuery("select request_id from UserRequest ur", Request.class);
        }

        return query.getResultList();
    }

    public List<Request> findInProgressRequestsByUser(String userId) {
        User user = getUserById(userId);
        if (user.getRole().equals("RUN")) {
            return findRequestsByUser(userId);
        } else {
            TypedQuery query = em.createQuery("select request_id from UserRequest ur where user_id = :user", Request.class);
            query.setParameter("user", user);
            return query.getResultList();
        }
    }


    public List<Request> findPickUpRequests() {
        TypedQuery<Request> openDeliveryRequestsQuery = em.createQuery("select request_id from UserRequest ur where acceptedBy is not null", Request.class);
        return openDeliveryRequestsQuery.getResultList();
    }

    public List<Request> findUserRequestsPickUp(String userId) {
        TypedQuery<Request> query = em.createQuery("select request_id from UserRequest ur where closedBy = :user_id", Request.class);
        query.setParameter("user_id", getUserById(userId));
        return query.getResultList();
    }

    public List<Request> findDeliveryRequests() {
        TypedQuery<Request> openPickUpRequestsQuery = em.createQuery("select request_id from UserRequest ur where acceptedBy is null", Request.class);
        return openPickUpRequestsQuery.getResultList();
    }

    public List<Request> findUserRequestsDelivery(String userId) {
        TypedQuery<Request> query = em.createQuery("select request_id from UserRequest ur where acceptedBy = :user_id", Request.class);
        query.setParameter("user_id", getUserById(userId));
        return query.getResultList();
    }

    public List<UserRequest> findUserRequestsWithClosedAcceptedBy(String userId) {
        TypedQuery query = em.createQuery("select ur from UserRequest ur where user_id = :user", UserRequest.class);
        query.setParameter("user", getUserById(userId));
        return query.getResultList();
    }
}
