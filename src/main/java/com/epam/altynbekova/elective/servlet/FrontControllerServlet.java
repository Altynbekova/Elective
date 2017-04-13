package com.epam.altynbekova.elective.servlet;

import com.epam.altynbekova.elective.action.Action;
import com.epam.altynbekova.elective.action.ActionFactory;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.ActionFactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.altynbekova.elective.util.ActionConstant.ACTION_PARAM;
import static com.epam.altynbekova.elective.util.ActionConstant.REDIRECT;

public class FrontControllerServlet extends HttpServlet {
    private final static Logger LOG = LoggerFactory.getLogger(FrontControllerServlet.class);
    private static final String SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    private static final String SERVLET_ERROR_EXCEPTION = "javax.servlet.error.exception";
    private static final String SERVLET_ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    private static final String JSP_DIRECTORY_PATH = "/WEB-INF/jsp/";
    private static final String ERROR_PAGE = "error-page";
    private static final String JSP_EXTENSION = ".jsp";
    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        actionFactory = new ActionFactory();
        try {
            actionFactory.loadProperties();
        } catch (ActionFactoryException e) {
            LOG.error("Cannot load properties for Action Factory.", e.getMessage(), e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer errorCode = (Integer) req.getAttribute(SERVLET_ERROR_STATUS_CODE);
        if (errorCode != null) {
            Throwable exception = (Throwable) req.getAttribute(SERVLET_ERROR_EXCEPTION);
            Object errorRequestUri = req.getAttribute(SERVLET_ERROR_REQUEST_URI);
            LOG.error("Servlet error with status code {}={}, request uri {}={}",
                    SERVLET_ERROR_STATUS_CODE, req.getAttribute(SERVLET_ERROR_STATUS_CODE),
                    SERVLET_ERROR_REQUEST_URI, errorRequestUri, exception);
            req.getRequestDispatcher(JSP_DIRECTORY_PATH + ERROR_PAGE + errorCode + JSP_EXTENSION).forward(req, resp);
        }

        String actionName = req.getParameter(ACTION_PARAM);
        try {
            LOG.debug("!!!!actionName=[{}]", actionName);
            Action action = actionFactory.getAction(actionName);
            String view = action.execute(req, resp);

            if (view.contains(REDIRECT)) {
                // fire redirect in case of a view change as result of the action (PRG pattern).
                resp.sendRedirect(req.getContextPath() + view.substring(REDIRECT.length()));
            } else {
                req.getRequestDispatcher(JSP_DIRECTORY_PATH + view + JSP_EXTENSION).forward(req, resp);
            }

        } catch (ActionFactoryException | ActionException e) {
            LOG.error("Cannot create/execute action with actionName={}", actionName, e);
        }
    }
}
