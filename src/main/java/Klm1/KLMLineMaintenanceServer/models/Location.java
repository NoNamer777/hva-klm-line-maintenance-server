package Klm1.KLMLineMaintenanceServer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Location")
@NamedQueries({
        @NamedQuery(name="find_all_locations", query = "select l from Location l"),
})
public class Location {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    @JsonIgnore
    private List<Request> requests;

    @Id
    private String location;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Location() {
    }

    public Location(List<Request> requests, String location, Type type) {
        this.requests = requests;
        this.location = location;
        this.type = type;
    }

    public enum Type {
        Pier, Buffer
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return this.location;
    }
}


