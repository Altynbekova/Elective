package com.epam.altynbekova.elective.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final String ENCODE_INIT_PARAM = "encoding";
    private String defaultEncoding = "utf-8";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding(defaultEncoding);
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

        String encodingParam = filterConfig.getInitParameter(ENCODE_INIT_PARAM);
        if (encodingParam != null) defaultEncoding = encodingParam;

    }

    public void destroy() {
    }
}
