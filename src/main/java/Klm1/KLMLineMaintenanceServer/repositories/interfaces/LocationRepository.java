package Klm1.KLMLineMaintenanceServer.repositories.interfaces;

import Klm1.KLMLineMaintenanceServer.models.Location;

import java.util.List;

public interface LocationRepository {

    List<Location> findAll();

    Location findById(String location);

    void save(Location location);

    void delete(Location location);
}
