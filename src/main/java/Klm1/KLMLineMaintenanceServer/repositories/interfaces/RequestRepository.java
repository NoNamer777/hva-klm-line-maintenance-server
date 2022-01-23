package Klm1.KLMLineMaintenanceServer.repositories.interfaces;

import Klm1.KLMLineMaintenanceServer.models.Request;

import java.util.List;

public interface RequestRepository {


    List<Request> findAll();

    Request findById(String id);

    Request save(Request request, String userId);

    Request setRequestStatus(String id, Request.Status status);

    List<Request> findRequestsByStatus(Request.Status status);

    void addRunnerToRequest(Request request, String runnerId);

    List<Request> findRunnerAcceptedRequests(String runnerId);
}
