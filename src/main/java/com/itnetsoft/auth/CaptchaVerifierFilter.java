package com.itnetsoft.auth;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class CaptchaVerifierFilter extends OncePerRequestFilter {
    protected static Logger logger = LogManager.getLogger("filter");
    private boolean useProxy = false;
    private String proxyPort;
    private String proxyHost;
    private String failureUrl;
    private BeforeCapthaFilter captchaFilter;
    private String privateKey;
    // Inspired by log output: AbstractAuthenticationProcessingFilter.java:unsuccessfulAuthentication:320)
    // Delegating to authentication failure handlerorg.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler@15d4273
    private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (captchaFilter.getRecaptchaResponse() != null) {
            // Create a new recaptcha (by Soren Davidsen)
            ReCaptchaImpl reCaptcha = new ReCaptchaImpl();

            // Set the private key (assigned by Google)
            reCaptcha.setPrivateKey(privateKey);

            // Assign proxy if needed
            if (useProxy) {
                Properties systemSettings = System.getProperties();
                systemSettings.put("http.proxyPort",proxyPort);
                systemSettings.put("http.proxyHost",proxyHost);
            }

            // Send HTTP request to validate user's Captcha
            ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(captchaFilter.getRemoteAddress(),
                    captchaFilter.getRecaptchaChallenge(), captchaFilter.getRecaptchaResponse());
            // Check if valid
            if (!reCaptchaResponse.isValid()) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
            // Reset Captcha fields after processing
            // If this method is skipped, everytime we access a page
            // CaptchaVerifierFilter will infinitely send a request to the Google Captcha service!
            resetCaptchaFields();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        // Proceed with the remaining filters
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public void resetCaptchaFields() {
        captchaFilter.setRecaptchaChallenge(null);
        captchaFilter.setRecaptchaResponse(null);
        captchaFilter.setRemoteAddress(null);
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getFailureUrl() {
        return failureUrl;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    public BeforeCapthaFilter getCaptchaFilter() {
        return captchaFilter;
    }

    public void setCaptchaFilter(BeforeCapthaFilter captchaFilter) {
        this.captchaFilter = captchaFilter;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
