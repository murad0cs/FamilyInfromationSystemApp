package com.app.userinformationsystem.repository;

import com.app.userinformationsystem.model.User;
import com.app.userinformationsystem.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByUserType(UserType userType);
    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    Optional<User> findByUserId(@Param("userId") Long userId);
    //Optional<User> findByUserId(Long userId);
}
