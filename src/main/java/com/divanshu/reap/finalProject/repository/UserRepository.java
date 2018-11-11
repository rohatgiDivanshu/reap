package com.divanshu.reap.finalProject.repository;

import com.divanshu.reap.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);

    User findByEmail(String email);

    List<User> findByFirstnameLike(String firstname);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByResetToken(String resetToken);

    User findByPassword(String password);

//    @Query("select first_name from user where first_name like "___%"")
//    List<User> findByFirstname(String firstname);

}
