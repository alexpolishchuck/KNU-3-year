package com.example.demo.servlets;

import com.example.demo.security.*;
import com.example.demo.users.BankRepository;
import com.example.demo.users.BankService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class loginServlet extends HttpServlet {

    public loginServlet()
    {
        super();
        authService = new AuthService(
                new BankService(new BankRepository()),
                new JwtService());
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //getServletContext().getRequestDispatcher("/resources/pages/authenticate.jsp").forward(req, resp);

        JSONObject json = new JSONObject();
        json.put("form",
                "<form action=\"\" style=\"min-width: 50%\">\n" +
                        "    <h1 style=\"text-align: center\" class=\"mt-3\">SIGN IN</h1>\n" +
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
                        "        <button id=\"signInBtn\" class=\"btn btn-warning w-l\">Sign in</button>\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "  </form>");
        json.put("script",
                "initSignIn();\n" +
                        "function initSignIn()" +
                        "{  console.log(\"sign in\")\n" +
                        "$(\"#signInBtn\").on(\"click\", function (event) {\n" +
                                        "      let formData = {\n" +
                                        "        name: $(\"#name\").val(),\n" +
                                        "        password: $(\"#password\").val()\n" +
                                        "      };\n" +
                                        "\n" +
                                        "      $.ajax({\n" +
                                        "        type: \"POST\",\n" +
                                        "        url: \"login\",\n" +
                                        "        data: formData,\n" +
                                        "        encode: true,\n" +
                                        "        success : function(token) {\n" +
                                        "          if(token && token.length !== 0)\n" +
                                        "          {\n" +
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

        resp.setContentType("application/json");
        resp.getWriter().write(json.toString());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(req.getParameter("name"));
        registerRequest.setPassword(req.getParameter("password"));

        AuthenticationResponse response = authService.authenticate(registerRequest);

        if(response.getToken() != null) {
            resp.setContentType("text/plain");
            resp.getWriter().write(response.getToken());
        }

      //  getServletContext().getRequestDispatcher("/welcome_page").forward(req, resp);
    }

    private AuthService authService;
}
