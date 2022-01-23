package Klm1.KLMLineMaintenanceServer.repositories.interfaces;

import Klm1.KLMLineMaintenanceServer.models.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User save(User user);

    void delete(User user);

    User findById(String id);
}
