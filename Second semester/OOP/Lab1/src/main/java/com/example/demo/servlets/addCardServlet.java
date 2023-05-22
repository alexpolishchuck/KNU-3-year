package com.example.demo.servlets;

import com.example.demo.cards.Card;
import com.example.demo.cards.CardsRepository;
import com.example.demo.cards.CardsService;
import com.example.demo.security.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "addCardServlet", value = "/user_page/add_card")
public class addCardServlet extends HttpServlet {

    public addCardServlet()
    {
        cardsService = new CardsService(new CardsRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/resources/pages/add_card.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Card card = new Card();
        card.setNumber(request.getParameter("number"));
        card.setOwners_name((String)request.getAttribute("username"));

        cardsService.addCard(card);
    }

    private CardsService cardsService;
}
