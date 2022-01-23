package Klm1.KLMLineMaintenanceServer.repositories.interfaces;

import Klm1.KLMLineMaintenanceServer.models.UserRequest;

import java.util.List;

public interface UserRequestRepository {
    public List<UserRequest> findAll();

    }
