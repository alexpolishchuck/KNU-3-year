package com.example.demo.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardsService {
    @Autowired
    public CardsService(CardsRepository repository)
    {
        this.cardsRepository = repository;
    }

    public List<String> get_cards()
    {
       return cardsRepository.test_cards();
    }
    private CardsRepository cardsRepository;
}
