package com.example.demo;

import com.example.demo.cards.Card;
import com.example.demo.cards.CardsService;
import com.example.demo.security.AuthService;
import com.example.demo.security.AuthenticationResponse;
import com.example.demo.security.RegisterRequest;
import com.example.demo.users.BankService;
import com.example.demo.users.Roles;
import java.util.List;

//@Controller
public class MainController {
/*
    @Autowired
    public MainController(
            BankService service,
            CardsService cardsService,
            AuthService authService)
    {
        this.bankService = service;
        this.cardsService = cardsService;
        this.authService = authService;
    }
    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String token)
    {
       if(token != null)
           model.addAttribute("token", token);

       return "index";
    }
    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("request", new RegisterRequest());
        return "register";
    }
    @PostMapping("/register")
    public RedirectView registerNew
            (@ModelAttribute RegisterRequest request)
    {
        authService.register(request);
        return new RedirectView("");
    }

    @GetMapping("/authenticate")
    public String authenticate(Model model)
    {
        model.addAttribute("request", new RegisterRequest());
        return "authenticate";
    }

    @PostMapping("/authenticate")
    public RedirectView authenticateNew
            (@ModelAttribute RegisterRequest request, RedirectAttributes attributes)
    {
        System.out.println(request.getName());
        AuthenticationResponse response = authService.authenticate(request);

        if(response.getToken() == null)
        {
            return new RedirectView("authenticate");
        }

        attributes.addAttribute("token", response.getToken());

        return new RedirectView("/");
    }

    @GetMapping("/authorize")
    RedirectView authorize()
    {
        if(SecurityContextHolder.getContext().getAuthentication() == null)
            return new RedirectView("");

        var authorities
                = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if(authorities.contains(new SimpleGrantedAuthority(Roles.ADMIN.name())))
            return new RedirectView("/admin_page");

        return new RedirectView("/user_page");
    }

    /*@GetMapping("/admin_page")
    public String admin_page()
    {
        return "admin_page";
    }*/
/*
    @PostMapping("/user_page/block_card")
    public ResponseEntity<String> block_card(@RequestParam String card_number)
    {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        if(cardsService.has_card(card_number, name)) {
            cardsService.set_blocked_status(card_number, true);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    @GetMapping("/admin_page/unblock_card")
    String unblock_card_form(Model model)
    {
        model.addAttribute("card", new Card());

        return "unblock_card";
    }
    @PostMapping("/admin_page/unblock_card")
    RedirectView unblock_card(@ModelAttribute Card card)
    {
        cardsService.set_blocked_status(card.getNumber(), false);

        return new RedirectView("/admin_page");
    }
    @GetMapping("/user_page")
    public String user_page(Model model)
    {

        List<Card> cards = cardsService.get_users_cards(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("cards", cards);

        return "user_page";
    }

    @PostMapping("/logout")
    public void logout()
    {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @GetMapping("/user_page/add_card")
    public String add_card(Model model)
    {
        model.addAttribute("card", new Card());

        return "add_card";
    }
    @PostMapping("/user_page/add_card/new")
    public RedirectView add_new_card(@ModelAttribute Card card)
    {
        if(SecurityContextHolder.getContext().getAuthentication() == null)
            return new RedirectView("");

        card.setOwners_name(SecurityContextHolder.getContext().getAuthentication().getName());
        cardsService.addCard(card);

        return new RedirectView("/user_page");
    }
    private BankService bankService;
    private CardsService cardsService;
    private AuthService authService;

 */
}
