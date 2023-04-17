package com.example.demo.cards;

import com.example.demo.users.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardsService {
    @Autowired
    public CardsService(CardsRepository repository)
    {
        this.cardsRepository = repository;
    }

    public List<Card> get_users_cards(String username)
    {
       return cardsRepository.find_users_cards(username);
    }
    public Card get_card_by_number(String number)
    {
        return cardsRepository.find_card_by_number(number);
    }
    public void addCard(Card card) {
        try {
            cardsRepository.save(card);
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    @Transactional
    public void set_blocked_status(String number, boolean status)
    {
        try
        {
            cardsRepository.set_blocked_status(number, status);
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public boolean has_card(String number, String name)
    {
        Card card = cardsRepository.has_card(number, name);

        if(card != null)
            return true;

        return false;
    }

    private CardsRepository cardsRepository;
}
