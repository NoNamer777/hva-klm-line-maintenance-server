package Klm1.KLMLineMaintenanceServer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "aircraft")
@NamedQueries({
        @NamedQuery(name = "find_all_aircrafts", query = "select a from Aircraft a")
})
public class Aircraft {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aircraft")
    @JsonIgnore
    private List<Request> requests;

    @Id
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;


    private String manufacturer;


    public Aircraft() {

    }


    public Aircraft(List<Request> requests, int id, String name, Type type, String manufacturer) {
        this.requests = requests;
        this.id = id;
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
    }

   public  enum Type {
        NA, Wide_body, Narrow_body
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }


    public void setType(Type type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return this.name;
    }
}




