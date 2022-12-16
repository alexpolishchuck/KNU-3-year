package servlets;

import db.DataBaseManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utility.CrudMethod;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CrudController", value = "/CrudController")
public class CrudController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrudMethod method = CrudMethod.valueOf(request.getParameter("method"));
        if(method == null) {
            response.sendRedirect(request.getContextPath() + "/show_products");
            return;
        }
        try {
            DataBaseManager dbm = new DataBaseManager();
            String category = request.getParameter("category");
            String name = request.getParameter("name");
            switch (method)
            {
                case DEL:
                    if(category!=null && name!=null)
                    dbm.deleteElement(category,name);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        response.sendRedirect(request.getContextPath() + "/show_products");
    }

}
