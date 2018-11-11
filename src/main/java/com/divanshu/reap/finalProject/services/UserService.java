package com.divanshu.reap.finalProject.services;


import com.divanshu.reap.finalProject.entity.Role;
import com.divanshu.reap.finalProject.entity.User;
import com.divanshu.reap.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void deleteOneUser(Integer id) {
        userRepository.deleteById(id);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findOneUser(Integer id) {

        return userRepository.findById(id).get();
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public List<User> findByFirstNameLike(String firstname) {
        return userRepository.findByFirstnameLike("%" + firstname + "%");
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findUserByResetToken(String resetToken) {
        return userRepository.findUserByResetToken(resetToken);
    }


    public User updatePassword(String password) {
        return userRepository.findByPassword(password);
    }


    public void createUser(User user) {
        user.setDateCreated(new Date());
        user.setStatus("Active");
        Role userRole = new Role("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void createAdmin(User user) {
//        user.setEmail("admin@ttn.com");
//        user.setPassword("admin");
        user.setDateCreated(new Date());
        user.setStatus("Active");
        Role userRole = new Role("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void createSuperAdmin(User user) {
        user.setDateCreated(new Date());
        user.setStatus("Active");
        Role userRole = new Role("SUPER_ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }


    public boolean isUserPresent(String email) {

        User user = userRepository.findByEmail(email);
        return user != null;
    }

    public List<User> findAll() {

        List<User> userList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userList.add(user);
        }
        return userList;
    }


    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstnameLike(firstName + "%");
    }

}


