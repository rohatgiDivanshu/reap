package com.divanshu.reap.finalProject.services;


import com.divanshu.reap.finalProject.entity.Badges;
import com.divanshu.reap.finalProject.entity.User;
import com.divanshu.reap.finalProject.enums.UserRole;
import com.divanshu.reap.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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


//    public User updatePassword(String password) {
//        return userRepository.findByPassword(password);
//    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        Iterator<User> iterator = userRepository.findAll().iterator();
        if (!iterator.hasNext()) {
            provideDefaultBadgeByRole(user);
            userRepository.save(user);
        } else {
            provideDefaultBadgeByRole(user);
            userRepository.save(user);
        }
        return user;
    }


//    public User findByUsername(String username) {
//        return userRepository.findByUserName(username);
//    }

//    public Optional<User> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

//    public List<User> getAllUsersByRole(String role) {
//        return userRepository.getUsersByRole(role);
//    }


//    public List<User> getUsersFirstName(String firstname) {
//        return userRepository.findByFirstNameLike("%" + firstname + "%");
//    }

    /*
     * Method to fetch all the users
     * */
//    public List<User> getAllUsersList() {
//        return userRepository.findAll();
//    }
/*
    public User updateUserDetails(String role, String status, Integer id) {
        User user = userRepository.getUserById(id);
        user.setUserRole(role);
        user.setStatus(status);
        provideDefaultBadgeByRole(user);
        return userRepository.save(user);

    }*/

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public User getById(Integer id) {
        return userRepository.getUserById(id);
    }

    /*
     * Setting Default Badge on the basis of role
     * */
    public User provideDefaultBadgeByRole(User user) {
        if (user.getUserRole().equals("USER")) {
            user.setBadges(new Badges(3, 2, 1));
        } else if (user.getUserRole().equals("SUPERVISOR")) {
            user.setBadges(new Badges(6, 3, 2));
        } else if (user.getUserRole().equals("PRACTICE HEAD")) {
            user.setBadges(new Badges(9, 6, 3));
        } else {
            user.setBadges(new Badges(0, 0, 0));
        }
        return user;
    }

    public void createUser(User user) {
        user.setDateCreated(new Date());
        user.setStatus("Active");
        user.setUserRole("USER");
        provideDefaultBadgeByRole(user);
        userRepository.save(user);
    }

    public void createAdmin(User user) {
        user.setDateCreated(new Date());
        user.setStatus("Active");
        user.setUserRole("ADMIN");
        provideDefaultBadgeByRole(user);
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

    public Page<User> findAllUserPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}


