package Klm1.KLMLineMaintenanceServer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_request")
@IdClass(UserRequest.UrequestCPK.class)
@NamedQueries({
        @NamedQuery(name = "find_user_request_by_request_id", query = "SELECT r FROM UserRequest r WHERE r.request_id = :request"),
        @NamedQuery(name = "find_all_by_runner_accepted_requests", query = "SELECT r.request_id FROM UserRequest r WHERE r.acceptedBy = :runner_id"),
        @NamedQuery(name = "find_all_by_engineer_created_requests", query = "SELECT r.request_id FROM UserRequest r WHERE r.user_id = :engineer"),
        @NamedQuery(name = "find_all_user_requests", query = "select ur from UserRequest ur "),
        @NamedQuery(name = "find_all_requests_created_and_accepted_by", query = "select ur.request_id from UserRequest ur WHERE ur.user_id = :user AND ur.acceptedBy = :userId")
})
public class UserRequest {

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user_id;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "request_id")
    private Request request_id;


    @Column(name = "acceptedBy", nullable = true)
    private String acceptedBy;

    @Column(name = "closedBy", nullable = true)
    private String closedBy;

    public UserRequest() {
    }

    public UserRequest(@NotNull User user_id, @NotNull Request request_id) {
        this.user_id = user_id;
        this.request_id = request_id;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static class UrequestCPK implements Serializable {


        private String user_id;


        private String request_id;

        public UrequestCPK() {
        }

        public UrequestCPK(String user_id, String request_id) {
            this.user_id = user_id;
            this.request_id = request_id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UrequestCPK that = (UrequestCPK) o;
            return user_id == that.user_id &&
                    Objects.equals(request_id, that.request_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user_id, request_id);
        }


    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Request getRequest_id() {
        return request_id;
    }

    public void setRequest_id(Request request_id) {
        this.request_id = request_id;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }
}







