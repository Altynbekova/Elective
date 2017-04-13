package com.epam.altynbekova.elective.filter;

import com.epam.altynbekova.elective.entity.Role;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.PropertyManagerException;
import com.epam.altynbekova.elective.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epam.altynbekova.elective.util.ActionConstant.ACTION_PARAM;
import static com.epam.altynbekova.elective.util.ActionConstant.USER_ATTRIBUTE;

public class RoleActionsFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(RoleActionsFilter.class);
    private static final String ROLE_ACTIONS_FILE_NAME = "role-actions.properties";
    private static final String ANONYM_PREFIX = "anonym";
    private static final String STUDENT_PREFIX = "student";
    private static final String LECTURER_PREFIX = "lecturer";
    private List<String> anonymActions = new ArrayList<>();
    private List<String> studentActions = new ArrayList<>();
    private List<String> lecturerActions = new ArrayList<>();
    private Set<String> allActions = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            PropertyManager pm = new PropertyManager(ROLE_ACTIONS_FILE_NAME);
            anonymActions = pm.getPropertyValues(ANONYM_PREFIX);
            studentActions = pm.getPropertyValues(STUDENT_PREFIX);
            lecturerActions = pm.getPropertyValues(LECTURER_PREFIX);
            allActions = pm.getPropertyValues();
        } catch (PropertyManagerException e) {
            LOG.error("Cannot open file {}", ROLE_ACTIONS_FILE_NAME, e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String actionName = req.getParameter(ACTION_PARAM);

        final User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        List<String> actionList;

        if (user != null) {
            actionList = user.getRole().getValue().equals(Role.STUDENT.getValue()) ? studentActions : lecturerActions;
        } else
            actionList = anonymActions;

        LOG.debug("Got action list {} for user {}", actionList.toArray(), user);

        if (!allActions.contains(actionName)) {
            LOG.error("Cannot find action with actionName={}", actionName);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else {
            if (!actionList.contains(actionName)) {
                LOG.error("Action with actionName={} is forbidden for user {}", actionName, user);
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
