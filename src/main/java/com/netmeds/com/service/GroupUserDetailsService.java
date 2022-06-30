package com.netmeds.com.service;

import com.netmeds.com.dto.UserRequest;
import com.netmeds.com.entity.User;
import com.netmeds.com.exceptionHandling.UserNotFoundException;
import com.netmeds.com.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUserName(username);
        return user.map(GroupUserDetails:: new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
    }




    public User saveUser(UserRequest userRequest) {
        User user = User.
                build(0, userRequest.getUserName(),userRequest.getPassword(),
                        userRequest.isActive(),userRequest.getRoles());
        return repository.save(user);
    }


    public List<User> getALlUsers() {
        return repository.findAll();
    }


    public User getUser(int id) throws UserNotFoundException {
        User user= repository.findById(id);
        if(user!=null){
            return user;
        }else{
            throw new UserNotFoundException("user not found with id : "+id);
        }
    }
}



