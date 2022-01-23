package Klm1.KLMLineMaintenanceServer.controllers;

import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.repositories.UserRepositoryJpa;
import Klm1.KLMLineMaintenanceServer.repositories.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("")
  public List<User> getUsers() {
    System.out.println(userRepository.findAll());
    return (List<User>) userRepository.findAll();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable(name = "id") String id){
    User user= userRepository.findById(id);
    if(user== null){
      throw new RuntimeException("User with id:" + id + "not found");
    }
    return user;
  }

    @PostMapping(value = "")
    public String postUsers(@RequestBody User user) {
        List<User> users = getUsers();

        String lastId = users.get(users.size() - 1).getId();
        int lastIdNr = Integer.parseInt(lastId.substring(3));

        String newId = String.format("KLM%05d", ++lastIdNr);
        user.setId(newId);

        System.out.println("Id: " + user.getId());

        userRepository.save(user);
        return "User saved succesfully";
    }

    @PutMapping(value = "")
    public String updateUser(@RequestBody User user) {
        userRepository.save(user);
        return "updated successfully";
    }

  @DeleteMapping(value = "/{id}")
  public String deleteUser(@PathVariable String id){
    User user= userRepository.findById(id);
    userRepository.delete(user);
    return "Delete is successfull";
  }


}
