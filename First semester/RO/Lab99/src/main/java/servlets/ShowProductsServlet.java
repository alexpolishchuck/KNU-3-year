package servlets;

import db.DataBaseManager;
import db.vegetable;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.CrudMethod;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "ShowProducts", urlPatterns = {"/show_products"})
public class ShowProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<vegetable> vegs = null;
        try {
            DataBaseManager dbm = new DataBaseManager();
            vegs = dbm.showTable();
        } catch (Exception e) {
           e.printStackTrace();
           System.out.println("EXCEPTION");
        }
        req.setAttribute("Elements", vegs);
        getServletContext().getRequestDispatcher("/ShowProducts.jsp").forward(req,resp);
    }


}
