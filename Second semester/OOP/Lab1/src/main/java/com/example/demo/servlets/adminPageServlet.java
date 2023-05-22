package com.example.demo.servlets;

import com.example.demo.cards.Card;
import com.example.demo.cards.CardsRepository;
import com.example.demo.cards.CardsService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin_page")
public class adminPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/resources/pages/admin_page.jsp").forward(req, resp);
    }
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public adminPageServlet()
    {
        cardsService = new CardsService(new CardsRepository());
    }
    private CardsService cardsService;
}
