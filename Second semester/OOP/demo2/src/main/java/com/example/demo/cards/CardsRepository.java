package com.example.demo.cards;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends CrudRepository<Card, Integer> {
    @Query(value = "SELECT c FROM Card c WHERE c.owners_name = ?1")
    List<Card> find_users_cards(String username);
    @Query(value = "SELECT c FROM Card c WHERE c.number = ?1")
    Card find_card_by_number(String number);
    @Modifying
    @Query(value = "UPDATE Card c SET c.is_blocked = ?2 WHERE c.number = ?1")
    void set_blocked_status(String number, Boolean status);
    @Query(value = "SELECT c FROM Card c WHERE c.number = ?1 AND c.owners_name = ?2")
    Card has_card(String number, String name);
}
