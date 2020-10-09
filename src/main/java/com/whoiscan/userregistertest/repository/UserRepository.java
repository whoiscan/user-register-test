package com.whoiscan.userregistertest.repository;

import com.whoiscan.userregistertest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsernameOrEmail(String userName,String email);
    Optional<User> findByActivCode(String activCode);

}
