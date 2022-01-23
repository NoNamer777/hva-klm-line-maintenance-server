package Klm1.KLMLineMaintenanceServer.repositories;

import Klm1.KLMLineMaintenanceServer.models.Equipment;
import Klm1.KLMLineMaintenanceServer.models.Request;
import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.models.UserRequest;
import Klm1.KLMLineMaintenanceServer.models.helper.UserNotFoundException;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RequestRepositoryJpa implements RequestRepository {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRequestRepositoryJpa userRequestRepositoryJpa;

    //  Get all
    @Override
    public List<Request> findAll() {
        TypedQuery<Request> namedQuery = em.createNamedQuery("find_all_requests", Request.class);
        return namedQuery.getResultList();
    }

    @Override
    public Request findById(String id) {
        return em.find(Request.class, id);
    }

    @Override
    public Request save(Request request, String userId) throws UserNotFoundException {
        User user = em.find(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        } else {
            Request request1 = em.merge(request);
            UserRequest userRequest = new UserRequest(user, request1);

            em.merge(userRequest);
            return request1;
        }
    }

    @Override
    //  Change request status
    public Request setRequestStatus(String id, Request.Status status) {
        Request request = em.find(Request.class, id);
        request.setStatus(status);
        return em.merge(request);
    }

    public Request saveSelfPickup(Request request, String userId) {
        User user = em.find(User.class, userId);
        //  Sets the requests status to in progress
        request.setStatus(Request.Status.IP);
        Request request1 = em.merge(request);
        //  Adds the user to the accepted by
        UserRequest userRequest =  new UserRequest(user, request1);
        userRequest.setAcceptedBy(userId);
        em.merge(userRequest);
        //  Sets the equipment to in use
        Equipment usedEquipment = em.find(Equipment.class, request.getEquipment().getSerialNumber());
        usedEquipment.setStatus(Equipment.Status.Inuse);
        em.merge(usedEquipment);
        return request1;
    }

    public List<Request> findSelfPickupList(String userId) {
        Query namedQuery = em.createNamedQuery("find_all_requests_created_and_accepted_by", Request.class);
        User user = em.find(User.class, userId);

        namedQuery.setParameter("user", user);
        namedQuery.setParameter("userId", userId);
        List<Request> requestList = namedQuery.getResultList();
        System.out.println(requestList);

        return requestList;
    }

    public Request closeSelfPickUp(String requestId, String userId) {
        Request request = em.find(Request.class, requestId);
        UserRequest userRequest = userRequestRepositoryJpa.findByRequest(request);
        Equipment equipment = em.find(Equipment.class, request.getEquipment().getSerialNumber());

        userRequest.setClosedBy(userId);
        request.setStatus(Request.Status.CL);

        if (equipment.getStatus() != Equipment.Status.Broken) {
            equipment.setStatus(Equipment.Status.Usable);
            em.merge(equipment);
        }
        em.merge(userRequest);
        return em.merge(request);
    }

    public void setRequestEquipment(Request request, Equipment equipment){
        Query query = em.createQuery("update Request r set r.equipment=?1 where r.id=?2");
        query.setParameter(1,equipment);
        query.setParameter(2,request.getId());
        em.merge(request);
    }

    public void setEquipmentForRequest(Request request, Equipment equipment){
        request.setEquipment(equipment);
        em.merge(request);
    }


    public List<Request> findRequestsByStatus(Request.Status status) {
        TypedQuery<Request> namedQuery = em.createNamedQuery("find_requests_by_status", Request.class);
        return namedQuery.setParameter("status", status).getResultList();
    }

    public void addRunnerToRequest(Request request, String runnerId) {
        TypedQuery<UserRequest> namedQuery = em.createNamedQuery("find_user_request_by_request_id", UserRequest.class);

        UserRequest userRequest = namedQuery.setParameter("request", request).getSingleResult();
        Equipment usedEquipment = em.find(Equipment.class, request.getEquipment().getSerialNumber());

        if (userRequest.getAcceptedBy() == null) {
            System.out.println(usedEquipment);
            usedEquipment.setStatus(Equipment.Status.Inuse);
            System.out.println(usedEquipment);
            em.merge(usedEquipment);

            userRequest.setAcceptedBy(runnerId);
        } else {
            userRequest.setClosedBy(runnerId);
        }

        em.merge(userRequest);
        request.setStatus(Request.Status.IP);
        em.merge(request);
    }

    public List<Request> findRunnerAcceptedRequests(int runnerId) {
        TypedQuery<Request> namedQuery = em.createNamedQuery("find_all_by_runner_accepted_requests", Request.class);
        return namedQuery.setParameter("runner_id", runnerId).getResultList();
    }

    public List<Request> findByEngineerCreatedRequests(String engineerId) {
        User engineer = em.find(User.class, engineerId);

        System.out.println(engineer);

        TypedQuery<Request> namedQuery = em.createNamedQuery("find_all_by_engineer_created_requests", Request.class);
        return namedQuery.setParameter("engineer", engineer).getResultList();
    }

    public List<Request> findRunnerAcceptedRequests(String runnerId) {
        TypedQuery<Request> namedQuery = em.createNamedQuery("find_all_by_runner_accepted_requests", Request.class);
        return namedQuery.setParameter("runner_id", runnerId).getResultList();
    }

    public List<Request> find_requests_order_by_departure(){
        TypedQuery<Request> query= em.createQuery("select r from Request r order by r.departure asc", Request.class);

        return query.getResultList();
    }

    public void closeRequestDelivery(String requestId) {
        Request request = em.find(Request.class, requestId);
        Equipment usedEquipment = em.find(Equipment.class, request.getEquipment().getSerialNumber());
        UserRequest userRequest = userRequestRepositoryJpa.findByRequest(request);

        if (userRequest.getClosedBy() != null) {
            if (usedEquipment.getStatus() != Equipment.Status.Broken) {
                usedEquipment.setStatus(Equipment.Status.Usable);
                em.merge(usedEquipment);
            }
        }

        request.setStatus(Request.Status.CL);
        em.merge(request);
    }

    public void cancelRequestRun(String requestId) {
        Request request = em.find(Request.class, requestId);
        UserRequest userRequest = userRequestRepositoryJpa.findByRequest(request);
        Equipment equipment = em.find(Equipment.class, request.getEquipment().getSerialNumber());

        if (userRequest.getClosedBy() == null) {
            userRequest.setAcceptedBy(null);
            request.setEquipment(null);
            if (equipment.getStatus() != Equipment.Status.Broken) {
                equipment.setStatus(Equipment.Status.Usable);
            }
        } else {
            userRequest.setClosedBy(null);
        }
        request.setStatus(Request.Status.OP);
        em.merge(userRequest);
        em.merge(request);
        em.merge(equipment);
    }

    public void requestPickUp(String requestId) {
        Request request = em.find(Request.class, requestId);
        request.setStatus(Request.Status.OP);
    }
}
