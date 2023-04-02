package com.example.demo.users;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

@org.springframework.stereotype.Repository
public interface BankRepository extends CrudRepository<Person, Integer> {

    @Query("SELECT p FROM Person p WHERE p.name = ?1")
    UserDetails findByUsername(String username);
}
