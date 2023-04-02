package com.example.demo.cards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends CrudRepository<Card, Integer> {
    @Query("SELECT c.number FROM Card c JOIN c.person p WHERE p.id = c.owners_id")
    List<String> test_cards();
}
