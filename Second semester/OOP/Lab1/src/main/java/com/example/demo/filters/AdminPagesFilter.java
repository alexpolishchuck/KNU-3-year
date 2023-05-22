package com.example.demo.filters;

import com.example.demo.security.JwtService;
import com.example.demo.users.BankRepository;
import com.example.demo.users.BankService;
import com.example.demo.users.Person;
import com.example.demo.users.Roles;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "AdminPagesFilter", urlPatterns = {"/admin_page/*"})
public class AdminPagesFilter implements Filter {
    public AdminPagesFilter()
    {
        super();
        jwtService = new JwtService();
        bankService = new BankService(new BankRepository());
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        final String authHeader = (httpServletRequest).getHeader("Authorization");
        final String jwt;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        jwt = authHeader.substring(7);

        if(jwtService.isTokenValid(jwt))
        {
            username = jwtService.extractUsername(jwt);
            Person user =  bankService.loadUserByUsername(username);

            if(username != null && user != null && user.getRole().equals(Roles.ADMIN))
            {
                request.setAttribute("username", username);
                chain.doFilter(request, response);
                return;
            }
        }

        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    private BankService bankService;
    private JwtService jwtService;
}
