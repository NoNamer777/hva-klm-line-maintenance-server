package Klm1.KLMLineMaintenanceServer.repositories.interfaces;

import Klm1.KLMLineMaintenanceServer.models.Aircraft;
import Klm1.KLMLineMaintenanceServer.models.User;

import java.util.List;

public interface AircraftRepository {

    List<Aircraft> findAll();

    Aircraft save(Aircraft aircraft);

    void delete(Aircraft aircraft);

    Aircraft findById(int id);
}
