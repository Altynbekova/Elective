package com.epam.altynbekova.elective.validator;

import com.epam.altynbekova.elective.exception.FormValidatorException;
import com.epam.altynbekova.elective.exception.PropertyManagerException;
import com.epam.altynbekova.elective.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;

public class FormValidator {
    private static final Logger LOG = LoggerFactory.getLogger(FormValidator.class);
    private static final String FORM_PROPERTY_FILE_NAME = "validator.properties";
    private static final String ERRORS_SUFFIX = "Errors";
    private static final String FORM = "form.";
    private static final String CLASS = "class";
    private static final String DOT = ".";
    private static final String DOT_REGEX = "[.]";
    private static Properties properties;
    private Map<String, List<String>> fieldErrors = new HashMap<>();

    public FormValidator() throws FormValidatorException {
        if (properties == null) {
            try {
                PropertyManager propertyManager = new PropertyManager(FORM_PROPERTY_FILE_NAME);
                properties = propertyManager.getProperties();
            } catch (PropertyManagerException e) {
                LOG.error("Cannot load properties for form validator from {} file", FORM_PROPERTY_FILE_NAME, e);
                throw new FormValidatorException("Cannot load form validator properties", e);
            }
        }
    }

    public boolean isValid(HttpServletRequest request, Map<String, List<String>> fieldErrors) {
        boolean isValid = true;
        if (!fieldErrors.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
                request.getSession().setAttribute(entry.getKey() + ERRORS_SUFFIX, entry.getValue());
            }
            isValid = false;
        }
        return isValid;
    }

    public Map<String, List<String>> validate(String formName, HttpServletRequest request) throws FormValidatorException {
        Enumeration<String> sessionAttributes = request.getSession().getAttributeNames();
        while (sessionAttributes.hasMoreElements()) {
            String element = sessionAttributes.nextElement();
            if (element.endsWith(ERRORS_SUFFIX))
                request.getSession().removeAttribute(element);
        }

        LOG.debug("Starting to get fields of form[{}] and corresponding validators...", formName);
        Map<String, List<Validator>> fieldsValidators = new HashMap<>();
        List<Validator> validators;
        Enumeration<String> attributeNames = request.getParameterNames();
        while (attributeNames.hasMoreElements()) {
            String field = attributeNames.nextElement();
            validators = getValidators(formName, field);
            fieldsValidators.put(field, validators);
        }
        LOG.debug("Finished getting validators for fields of form [{}]", formName);

        for (Map.Entry<String, List<Validator>> entry : fieldsValidators.entrySet()) {
            String key = entry.getKey();
            String parameterValue = request.getParameter(key);
            List<String> errorMessages = new ArrayList<>();
            for (Validator validator : entry.getValue()) {
                if (!validator.isValid(parameterValue)) {
                    errorMessages.add(validator.getMessage());
                }
            }
            if (!errorMessages.isEmpty())
                fieldErrors.put(key, errorMessages);
        }

        return fieldErrors;
    }

    private List<Validator> getValidators(String formName, String fieldName) throws FormValidatorException {
        List<Validator> validators = new ArrayList<>();

        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();

            String[] keyParts = key.split(DOT_REGEX);
            String validatorNumber = keyParts[keyParts.length - 2];
            StringBuilder validatorNumberFullName = new StringBuilder(FORM);
            validatorNumberFullName.append(formName).append(DOT).append(fieldName).append(DOT).append(validatorNumber);

            if (key.contains(validatorNumberFullName) && key.contains(CLASS)) {
                Validator validator = getValidator(validatorNumberFullName.toString(), value);
                validators.add(validator);
            }
        }
        return validators;
    }

    private Validator getValidator(String validatorNumberFullName, String keyValue) throws FormValidatorException {
        Class clazz;
        Validator validator;
        try {
            clazz = Class.forName(keyValue);
            validator = (Validator) clazz.newInstance();
            LOG.debug("Validator created {}", validator.getClass().getName());

            LOG.debug("Starting to fill fields of validator class...");
            Set<Map.Entry<Object, Object>> entries = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entries) {
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                String[] keyParts = key.split(DOT_REGEX);
                String validatorField = keyParts[keyParts.length - 1];

                if (key.contains(validatorNumberFullName)) {
                    setFieldFor(validator, validatorField, value);
                }
            }
            LOG.debug("Finished filling fields of validator class");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            LOG.error("Cannot create instance of validator class {}", keyValue, e);
            throw new FormValidatorException("Cannot create instance of validator", e);
        }

        return validator;
    }

    private void setFieldFor(Validator validator, String fieldName, String fieldValue) throws FormValidatorException {
        try {
            Object objValue = getPropertyValue(fieldValue);
            BeanInfo beanInfo = Introspector.getBeanInfo(validator.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor descriptor : descriptors) {
                if (!fieldName.equals(CLASS) && descriptor.getName().equals(fieldName))
                    descriptor.getWriteMethod().invoke(validator, objValue);
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            LOG.error("Cannot set value for validator field fieldName={}, fieldValue={}", fieldName,fieldValue);
            throw new FormValidatorException(MessageFormat.format(
                    "Cannot set value for validator field {0}", fieldName), e);
        }
    }

    private Object getPropertyValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return value;
        }
    }
}
