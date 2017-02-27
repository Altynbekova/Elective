package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionFactoryException;
import com.epam.altynbekova.elective.exception.PropertyManagerException;
import com.epam.altynbekova.elective.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

public class ActionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ActionFactory.class);
    private static final String ACTION_PROPS_FILE_NAME = "action.properties";
    private PropertyManager propertyManager;

    public void loadProperties() throws ActionFactoryException {
        try {
            propertyManager = new PropertyManager(ACTION_PROPS_FILE_NAME);
        } catch (PropertyManagerException e) {
            throw new ActionFactoryException(MessageFormat.format("Cannot load properties from file {}",
                    ACTION_PROPS_FILE_NAME), e);
        }
    }

    public Action getAction(String actionName) throws ActionFactoryException {
        try {
            String className = propertyManager.getProperty(actionName);
            Class actionClass = Class.forName(className);
            return (Action) actionClass.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | PropertyManagerException e) {
            throw new ActionFactoryException(MessageFormat.format("Cannot create instance of class for action{}",
                    actionName), e);
        }
    }
}
