package com.example.munchly.repositories;

import com.example.munchly.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByName(String name);

    Restaurant findByManagerId(Long managerId);

    Restaurant findByEmail(String email);
}
