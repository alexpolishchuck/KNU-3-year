package servlets;

import db.DataBaseManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "EditProduct", value = "/EditProduct")
public class EditProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/EditProduct.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DataBaseManager dbm = new DataBaseManager();
            String category = request.getParameter("category");
            String name = request.getParameter("name");
            String newval = request.getParameter("val");
            DataBaseManager.parameter parameter = DataBaseManager.parameter.valueOf(request.getParameter("field"));
            if(category!=null && name!=null && newval != null && parameter!=null)
                dbm.editElement(category,name,newval,parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/show_products");
    }
}
