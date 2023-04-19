package com.example.demo.servlets;

import com.example.demo.cards.Card;
import com.example.demo.cards.CardsService;
import com.example.demo.security.AuthService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.List;

@Component
public class adminPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Card> cards = cardsService.get_users_cards(SecurityContextHolder.getContext().getAuthentication().getName());
        req.setAttribute("cards", cards);
        getServletContext().getRequestDispatcher("/admin_page.html").forward(req, resp);
    }
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Autowired
    private CardsService cardsService;
}
