package Klm1.KLMLineMaintenanceServer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "User")
@NamedQueries({
        @NamedQuery(name = "find_all_users", query = "select u from User u "),
})
public class User {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user_id")
    @JsonIgnore
    private List<UserRequest> userRequests;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
//  @Enumerated(EnumType.STRING)
    private String role;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    public User() {
    }

    public User(String id, String name, String role, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.status = "OFF";
    }

    public User(List<UserRequest> userRequests, String id, String name, String role, String password, String status) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.status = status;
    }

    public enum Role {
        GE, RUN, ADM
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRequest> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(List<UserRequest> userRequests) {
        this.userRequests = userRequests;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return this.id.equals(user.getId());
    }

    public boolean validateEncodedPassword(String password) {
        return this.password.equals(password);
    }

}

