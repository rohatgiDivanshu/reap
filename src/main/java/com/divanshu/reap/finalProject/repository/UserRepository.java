package com.divanshu.reap.finalProject.repository;

import com.divanshu.reap.finalProject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
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

//    String findByStatus(String status);

//    String findByUserRole(String role);


//    User findByUserName(String userName);


//    Optional<User> findByEmail(String email);

//    List<User> findByFirstNameLike(String firstname);

    List<User> findAll();

    User getUserById(Integer id);

    @Override
    Page<User> findAll(Pageable pageable);
}
