package com.demo.demoJwt.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
  Optional<Restaurant> findByEmail(String email);

}
