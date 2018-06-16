package com.itnetsoft.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        if( "XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"))) {
            httpServletResponse.sendError(httpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
        } else {
            httpServletResponse.sendRedirect("/login");
        }

    }
}
