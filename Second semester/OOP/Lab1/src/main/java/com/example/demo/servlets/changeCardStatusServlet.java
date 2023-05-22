package com.example.demo.servlets;

import com.example.demo.cards.CardsRepository;
import com.example.demo.cards.CardsService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "changeCardStatusServlet", urlPatterns = {"/userPage/changeCardStatus", "/admin_page/changeCardStatus"})
public class changeCardStatusServlet extends HttpServlet {
    public changeCardStatusServlet()
    {
        cardsService = new CardsService(new CardsRepository());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{

            if(request.getRequestURL().toString().contains("/admin_page"))
                getServletContext()
                        .getRequestDispatcher("/resources/pages/unblock_card.jsp")
                        .forward(request, response);

        } catch (Exception ex)
        {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{

            if(request.getRequestURL().toString().contains("/admin_page"))
            {
                cardsService.set_blocked_status(request.getParameter("number"), false);
            }
            else
                cardsService.set_blocked_status(request.getParameter("number"), true);

        } catch (Exception ex)
        {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private CardsService cardsService;
}
