package com.example.demo;

import com.example.demo.cards.CardsService;
import com.example.demo.security.AuthenticationRequest;
import com.example.demo.security.AuthenticationResponse;
import com.example.demo.security.RegisterRequest;
import com.example.demo.users.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    public MainController(
            BankService service,
            CardsService cardsService)
    {
        this.bankService = service;
        this.cardsService = cardsService;
    }
    @GetMapping("/")
    public String index(Model model)
    {
       List<String> cards = cardsService.get_cards();
       model.addAttribute("cards", cards);
       return "index";
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register
            (@RequestBody RegisterRequest request)
    {

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate
            (@RequestBody AuthenticationRequest request)
    {

    }
    private BankService bankService;
    private CardsService cardsService;
}
