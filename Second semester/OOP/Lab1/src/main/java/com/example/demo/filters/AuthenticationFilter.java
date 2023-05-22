package com.example.demo.filters;

import com.example.demo.security.JwtService;
import com.example.demo.users.BankRepository;
import com.example.demo.users.BankService;
import com.example.demo.users.Person;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthenticationFilter implements Filter {

    public AuthenticationFilter()
    {
        super();
        jwtService = new JwtService();
        bankService = new BankService(new BankRepository());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if(httpServletRequest.getRequestURI().startsWith("/login") ||
                httpServletRequest.getRequestURI().startsWith("/register") ||
                httpServletRequest.getRequestURI().startsWith("/resources/img") ||
                httpServletRequest.getRequestURI().startsWith("/resources/styles") ||
                httpServletRequest.getRequestURI().startsWith("/resources/scripts") ||
                httpServletRequest.getRequestURI().startsWith("/resources/fonts") ||
                httpServletRequest.getRequestURI().startsWith("/welcome_page"))
        {
            chain.doFilter(request, response);
            return;
        }

        final String authHeader = (httpServletRequest).getHeader("Authorization");
        final String jwt;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            String url = httpServletRequest.getRequestURL().toString();
            httpServletRequest.setAttribute("requestedUrl", url);
            httpServletRequest.getRequestDispatcher("/welcome_page").forward(httpServletRequest, httpServletResponse);
            return;
        }

        jwt = authHeader.substring(7);

        try{
            if(jwtService.isTokenValid(jwt))
            {
                username = jwtService.extractUsername(jwt);
                Person user =  bankService.loadUserByUsername(username);

                if(username != null && user != null)
                {
                    request.setAttribute("username", username);
                    chain.doFilter(request, response);
                    return;
                }
            }
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        httpServletResponse.sendRedirect("/welcome_page");
    }

    public void destroy() {}
    private final BankService bankService;
    private final JwtService jwtService;
}