package com.whoiscan.userregistertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.whoiscan.userregistertest.entity.Card;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
    List<Card> getByUserId(Integer userId);

}
