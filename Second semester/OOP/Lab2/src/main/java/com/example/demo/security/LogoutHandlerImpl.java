package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        if (request.getSession() != null)
        {
            request.getSession().invalidate();
        }

        String returnTo = request.getScheme() + "://" + request.getServerName();

        if ((request.getScheme().equals("http") && request.getServerPort() != 80)
                || (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            returnTo += ":" + request.getServerPort();
        }

        returnTo += "/";

        String logoutUrl = String.format(
                "https://%s/v2/logout?client_id=%s&returnTo=%s",
                webConfig.domain,
                webConfig.clientId,
                returnTo);
        System.out.println(logoutUrl);
        try {
            response.sendRedirect(logoutUrl);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private final WebConfig webConfig;
}
