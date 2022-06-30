package com.netmeds.com.controller;

import com.netmeds.com.common.UserConstant;
import com.netmeds.com.dto.UserRequest;
import com.netmeds.com.entity.User;
import com.netmeds.com.exceptionHandling.UserNotFoundException;
import com.netmeds.com.repo.UserRepository;
import com.netmeds.com.service.GroupUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;





@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private GroupUserDetailsService service;


    @PostMapping("/join")
    public String joinGroup(@RequestBody User user) {
        user.setRoles(UserConstant.DEFAULT_ROLE);//USER
        String encryptedPwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPwd);
        repository.save(user);
        return "Hi " + user.getUserName() + " welcome to Netmeds group !";
    }
    //If loggedin user is ADMIN -> ADMIN OR HEADS
    //If loggedin user is MODERATOR -> HEADS


    @GetMapping("/access/{userId}/{userRole}")
    //@Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_HEADS')")
    public String giveAccessToUser(@PathVariable int id, @PathVariable String userRole, Principal principal) {
        User user = repository.findById(id);
        List<String> activeRoles = getRolesByLoggedInUser(principal);
        String newRole = "";
        if (activeRoles.contains(userRole)) {
            newRole = user.getRoles() + "," + userRole;
            user.setRoles(newRole);
        }
        repository.save(user);
        return "Hi " + user.getUserName() + " New Role assign to you by " + principal.getName();
    }


    @GetMapping
    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> loadUsers() {
        return repository.findAll();
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String testUserAccess() {
        return "user can only access this !";
    }

    private List<String> getRolesByLoggedInUser(Principal principal) {
        String roles = getLoggedInUser(principal).getRoles();
        List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if (assignRoles.contains("ROLE_ADMIN")) {
            return Arrays.stream(UserConstant.ADMIN_ACCESS).collect(Collectors.toList());
        }
        if (assignRoles.contains("ROLE_HEADS")) {
            return Arrays.stream(UserConstant.MODERATOR_ACCESS).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private User getLoggedInUser(Principal principal) {
        return repository.findByUserName(principal.getName()).get();
    }


    @PostMapping("/signup")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequest userRequest) {
        String encryptedPwd = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encryptedPwd);
        return new ResponseEntity<>(service.saveUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getALlUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(service.getUser(id));


    }


    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id){

        repository.deleteById(id);
        return "deleted successfully";


    }


}



