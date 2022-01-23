package Klm1.KLMLineMaintenanceServer.controllers;


import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.models.helper.AuthenticationException;
import Klm1.KLMLineMaintenanceServer.models.helper.AppconfigJ;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.UserRepository;
import Klm1.KLMLineMaintenanceServer.repositories.security.JWToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {

  @Autowired
  AppconfigJ appconfigJ;

  @Autowired
  UserRepository userRepo;

  @PostMapping("/users")
  public ResponseEntity<Object> createUser(@RequestBody ObjectNode signupInfo) {

    String id = signupInfo.get("iD") == null ? null : signupInfo.get("iD").asText();
    String password = signupInfo.get("passWord") == null ? null : signupInfo.get("passWord").asText();

    User user = new User();

    user.setId(id);
    user.setPassword("Not yet");


    User savedUser = userRepo.save(user);
    URI location = ServletUriComponentsBuilder.
      fromCurrentRequest().path("/{id}").
      buildAndExpand(savedUser.getId()).toUri();

    return ResponseEntity.created(location).body(savedUser);
  }



  @PostMapping("/login")
  public ResponseEntity<User> authenticateUser(@RequestBody ObjectNode signUpInfo) throws AuthenticationException {
    String password = signUpInfo.get("password") == null ? null : signUpInfo.get("password").asText();
    String id = signUpInfo.get("id") == null ? null : signUpInfo.get("id").asText();
    User user = userRepo.findById(id);

    if (user == null) throw new AuthenticationException("Invalid user and/or password");
    if (!user.validateEncodedPassword(password)) throw new AuthenticationException("Invalid user and/or password");

    JWToken jwToken = new JWToken(user.getName(), user.getId(), user.getRole(), user.getStatus());
    // Issue a token for the user valid for some time
    String tokenString = jwToken.encode(appconfigJ.passphrase, appconfigJ.expiration, appconfigJ.issuer);

    return ResponseEntity.accepted()
      .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
      .body(user);
  }


}
