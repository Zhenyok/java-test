package com.itnetsoft.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BeforeCapthaFilter extends OncePerRequestFilter {
    protected static Logger logger = LogManager.getLogger("filter");
    private String recaptchaResponse = null;
    private String recaptchaChallenge = null;
    private String remoteAddress = null;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getParameter("recaptcha_response_field") != null) {
            recaptchaResponse = httpServletRequest.getParameter("recaptcha_response_field");
            recaptchaChallenge = httpServletRequest.getParameter("recaptcha_challenge_field");
            remoteAddress = httpServletRequest.getRemoteAddr();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public String getRecaptchaResponse() {
        return recaptchaResponse;
    }

    public void setRecaptchaResponse(String recaptchaResponse) {
        this.recaptchaResponse = recaptchaResponse;
    }

    public String getRecaptchaChallenge() {
        return recaptchaChallenge;
    }

    public void setRecaptchaChallenge(String recaptchaChallenge) {
        this.recaptchaChallenge = recaptchaChallenge;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
