package com.example.demo.servlets;

import com.example.demo.cards.Card;
import com.example.demo.cards.CardsRepository;
import com.example.demo.cards.CardsService;
import com.example.demo.security.AuthService;
import com.example.demo.security.JwtService;
import com.example.demo.users.BankRepository;
import com.example.demo.users.BankService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "userServlet", value = "/userPage")
public class userServlet extends HttpServlet {

    public userServlet()
    {
        super();
        bankService = new BankService(new BankRepository());
        cardsService = new CardsService(new CardsRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String username = (String)request.getAttribute("username");
        List<Card> cards = cardsService.get_users_cards(username);

        request.setAttribute("cards", cards);
        getServletContext()
               .getRequestDispatcher("/resources/pages/user_page.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private BankService bankService;
    private CardsService cardsService;
}
