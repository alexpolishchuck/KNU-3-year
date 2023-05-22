package com.example.demo.controllers;

import com.auth0.AuthenticationController;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.example.demo.cards.Card;
import com.example.demo.cards.CardsService;
import com.example.demo.security.AuthService;
import com.example.demo.security.TokenAuthentication;
import com.example.demo.users.Roles;
import com.example.demo.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WelcomePageController {

    @Autowired
    public WelcomePageController(CardsService cardsService,
                                 AuthService authService) {
        this.cardsService = cardsService;
        this.authenticationController = authService.createAuthenticationController();
    }

    @GetMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUri = request.getScheme() + "://" + request.getServerName();
        if ((request.getScheme().equals("http") && request.getServerPort() != 80)
                || (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            redirectUri += ":" + request.getServerPort();
        }
        redirectUri += "/authentication_callback";

        String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
                .withParameter("scope", "openid profile email")
                .build();

        response.sendRedirect(authorizeUrl);
    }

    @RequestMapping("/authentication_callback")
    public void authentication_callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Parse the request
            Tokens tokens = authenticationController.handle(request, response);
            TokenAuthentication tokenAuth = new TokenAuthentication(JWT.decode(tokens.getIdToken()));
            SecurityContextHolder.getContext().setAuthentication(tokenAuth);
            response.sendRedirect("/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/login");
        }
    }

    @GetMapping("/")
    public String main_page(Model model)
    {
       return "main_page";
    }

    @RequestMapping("/register")
    public void register(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            String redirectUri = request.getScheme() + "://" + request.getServerName();
            if ((request.getScheme().equals("http") && request.getServerPort() != 80)
                    || (request.getScheme().equals("https") && request.getServerPort() != 443)) {
                redirectUri += ":" + request.getServerPort();
            }
            redirectUri += "/registration_callback";

            String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
                    .build();

            response.sendRedirect(authorizeUrl + "&screen_hint=signup");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/registration_callback")
    public void registration_callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Parse the request
            Tokens tokens = authenticationController.handle(request, response);
            TokenAuthentication tokenAuth = new TokenAuthentication(JWT.decode(tokens.getIdToken()));
            SecurityContextHolder.getContext().setAuthentication(tokenAuth);

            response.sendRedirect("/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/register");
        }
    }
    @GetMapping("/add_card")
    public String add_card(Model model)
    {
        model.addAttribute("card", new Card());

        return "add_card";
    }

    @PostMapping("/add_card")
    public RedirectView save_card(@ModelAttribute Card card)
    {
        if(SecurityContextHolder.getContext().getAuthentication() == null)
            return new RedirectView("");

        TokenAuthentication tokenAuthentication = (TokenAuthentication)SecurityContextHolder.getContext().getAuthentication();
        var claims = tokenAuthentication.getClaims();

        card.setOwners_name(claims.get("name").asString());
        cardsService.addCard(card);

        return new RedirectView("");
    }

    @GetMapping("/changeCardStatus")
    public String get_change_card_status(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try{
            var roles = AuthenticationUtils.get_roles_of_user();

            if(roles.contains(Roles.ADMIN.name()))
            {
                return "unblock_card";
            }

        } catch (Exception ex)
        {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "";
    }

    @PostMapping("/changeCardStatus")
    public void post_change_card_status(HttpServletRequest request, HttpServletResponse response) throws IOException{

        try{
            var roles = AuthenticationUtils.get_roles_of_user();

            if(roles.contains(Roles.ADMIN.name()))
            {
                cardsService.set_blocked_status(request.getParameter("cardNumber"), false);
                return;
            }

            String cardNumber = request.getParameter("cardNumber");

            cardsService.set_blocked_status(cardNumber, true);

        } catch (Exception ex)
        {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    private final CardsService cardsService;
    private final AuthenticationController authenticationController;
}
