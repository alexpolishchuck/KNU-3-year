package com.example.demo.servlets;

import com.example.demo.security.AuthService;
import com.example.demo.security.AuthenticationResponse;
import com.example.demo.security.JwtService;
import com.example.demo.security.RegisterRequest;
import com.example.demo.users.BankRepository;
import com.example.demo.users.BankService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet(name = "signUpServlet", value = "/register")
public class signUpServlet extends HttpServlet {

    public signUpServlet()
    {
        authService = new AuthService(new BankService(new BankRepository()), new JwtService());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getServletContext().getRequestDispatcher("/resources/pages/register.jsp").forward(request, response);
        JSONObject json = new JSONObject();
        json.put("form",
                "<form action=\"\" style=\"min-width: 50%\">\n" +
                        "    <h1 style=\"text-align: center\" class=\"mt-3\">SIGN UP</h1>\n" +
                        "    <div class=\"form-group row mt-5\">\n" +
                        "      <label for=\"name\" class=\"col-sm-1 col-form-label col-form-label-lg\">Name</label>\n" +
                        "      <div class=\"col-sm-11\" style=\"display: flex; justify-content: center\">\n" +
                        "        <input type=\"text\" class=\"form-control form-control-lg\" name=\"name\" id=\"name\" placeholder=\"Unique name\">\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "    <div class=\"form-group row mt-2\">\n" +
                        "      <label for=\"password\" class=\"col-sm-1 col-form-label col-form-label-lg\">Password</label>\n" +
                        "      <div class=\"col-sm-11\" style=\"display: flex; justify-content: center\">\n" +
                        "        <input type=\"password\" class=\"form-control form-control-lg\" name=\"password\" id=\"password\" placeholder=\"Password\">\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "    <div class=\"row mt-4\">\n" +
                        "      <div style=\"display: flex; justify-content: center; \">\n" +
                        "        <button id=\"registerBtn\" class=\"btn btn-warning w-l\">Sign up</button>\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "  </form>");
        json.put("script",
                "initSignUp();\n" +
                        "function initSignUp()" +
                        "{ console.log(\"sign up\")\n" +
                        "$(\"#registerBtn\").on(\"click\", function (event) {\n" +
                        "      let formData = {\n" +
                        "        name: $(\"#name\").val(),\n" +
                        "        password: $(\"#password\").val()\n" +
                        "      };\n" +
                        "\n" +
                        "      $.ajax({\n" +
                        "        type: \"POST\",\n" +
                        "        url: \"register\",\n" +
                        "        data: formData,\n" +
                        "        encode: true,\n" +
                        "        success : function(token) { console.log(\"SUCCESS SIGN UP\")\n" +
                        "          if(token && token.length !== 0)\n" +
                        "          { console.log(token) \n " +
                        "            localStorage.setItem(\"token\", token);\n" +
                        "            preloadFunc();\n" +
                        "          }\n" +
                        "\n" +
                        "        }\n" +
                        "      });\n" +
                        "\n" +
                        "      event.preventDefault();\n" +
                        "    });" +
                        "}");

        response.setContentType("application/json");
        response.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthenticationResponse authenticationResponse = authService.register(new RegisterRequest(request.getParameter("name"),
                request.getParameter("password")));

        if(authenticationResponse.getToken() != null) {
            response.setContentType("text/plain");
            response.getWriter().write(authenticationResponse.getToken());
        }
    }

    private AuthService authService;
}
