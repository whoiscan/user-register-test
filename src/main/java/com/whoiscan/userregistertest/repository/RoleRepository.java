package com.whoiscan.userregistertest.repository;

import com.whoiscan.userregistertest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role getByName(String name);
}
