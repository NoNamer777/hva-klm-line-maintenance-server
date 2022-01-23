package Klm1.KLMLineMaintenanceServer.controllers;

import Klm1.KLMLineMaintenanceServer.models.Request;
import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.models.UserRequest;
import Klm1.KLMLineMaintenanceServer.models.helper.AppconfigJ;
import Klm1.KLMLineMaintenanceServer.repositories.UserRequestRepositoryJpa;
import Klm1.KLMLineMaintenanceServer.repositories.security.JWToken;
import Klm1.KLMLineMaintenanceServer.repositories.security.JWTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRequestController {

    @Autowired
    private UserRequestRepositoryJpa userRequestRepositoryJpa;

    @Autowired
    AppconfigJ appconfigJ;

    private String getUserIdFromToken(String token) {
        String s = token.replace("Bearer ", "");
        JWTokenInfo jwTokenInfo = JWToken.decode(s, appconfigJ.passphrase);
        return jwTokenInfo.getId();
    }

    @GetMapping("/user-requests/full")
    public List<UserRequest> getUserRequestsWithClosedAcceptedBy(@RequestHeader(name = "Authorization") String token) {
        return userRequestRepositoryJpa.findUserRequestsWithClosedAcceptedBy(getUserIdFromToken(token));
    }

    @GetMapping("/user-requests")
    public List<Request> getUserRequests(@RequestHeader(name = "Authorization") String token) {
        return userRequestRepositoryJpa.findRequestsByUser(getUserIdFromToken(token));
    }

    @GetMapping("/user-requests/pickup")
    public List<Request> getPickUpRequests() {
        List<Request> requestList = userRequestRepositoryJpa.findPickUpRequests();
        List<Request> filteredList = new ArrayList<>();
        requestList.forEach(request -> {
            if (request.getStatus() == Request.Status.OP) {
                filteredList.add(request);
            }
        });
        return filteredList;
    }

    @GetMapping("/user-requests/delivery")
    public List<Request> getDeliveryRequests() {
        List<Request> requestList = userRequestRepositoryJpa.findDeliveryRequests();
        List<Request> filteredList = new ArrayList<>();
        requestList.forEach(request -> {
            if (request.getStatus() == Request.Status.OP) {
                filteredList.add(request);
            }
        });
        return filteredList;
    }

    @GetMapping("/user-requests/by")
    public List<Request> getUserRequestsByStatus(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "status") Request.Status status) {
        List<Request> requestList;

        List<Request> filteredList = new ArrayList<>();
        if (status == Request.Status.IP) {
            requestList = userRequestRepositoryJpa.findInProgressRequestsByUser(getUserIdFromToken(token));
            requestList.forEach(request -> {
                if (request.getStatus() == status) {
                    filteredList.add(request);
                }
            });
            return filteredList;
        } else {
            requestList = userRequestRepositoryJpa.findRequestsByUser(getUserIdFromToken(token));

            requestList.forEach(request -> {
                if (request.getStatus() == status) {
                    filteredList.add(request);
                }
            });
        }
        return filteredList;
    }

    @GetMapping("/user-requests/for-user/pick-up")
    public List<Request> getUserRequestsPickUp(@RequestHeader(name = "Authorization") String token) {
        return userRequestRepositoryJpa.findUserRequestsPickUp(getUserIdFromToken(token));
    }

    @GetMapping("/user-requests/for-user/delivery")
    public List<Request> getUserRequestsDelivery(@RequestHeader(name = "Authorization") String token) {
        return userRequestRepositoryJpa.findUserRequestsDelivery(getUserIdFromToken(token));
    }

}
