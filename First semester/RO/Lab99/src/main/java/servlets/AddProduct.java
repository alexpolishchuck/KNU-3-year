package servlets;

import db.DataBaseManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddProduct", value = "/AddProduct")
public class AddProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/AddProduct.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DataBaseManager dbm = new DataBaseManager();
            String category = request.getParameter("category");
            String name = request.getParameter("name");
            if(category!=null && name!=null)
            dbm.addElement(request.getParameter("category"), request.getParameter("name"));
        } catch (Exception e) {
           e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/show_products");
    }
}
