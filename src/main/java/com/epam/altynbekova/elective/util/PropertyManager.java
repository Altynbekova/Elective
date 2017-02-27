package com.epam.altynbekova.elective.util;

import com.epam.altynbekova.elective.exception.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PropertyManager {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyManager.class);
    private Properties properties;

    public PropertyManager(String fileName) throws PropertyManagerException {
        try (InputStream in = PropertyManager.class.getClassLoader().getResourceAsStream(fileName)) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new PropertyManagerException(MessageFormat.format("Cannot load file {}", fileName), e);
        }
    }

    public String getProperty(String key) throws PropertyManagerException {
        String propertyValue;
        if (properties.containsKey(key)) {
            propertyValue = properties.getProperty(key);
        } else {
            LOG.error("There is no key={} in properties file", key);
            throw new PropertyManagerException(MessageFormat.format("There is no key={} in properties file", key));
        }
        return propertyValue;
    }

    public List<String> getPropertyValues(String keyPrefix) throws PropertyManagerException {
        List<String> propertyValues = new ArrayList<>();
        if (!properties.isEmpty()) {
            propertyValues.addAll(properties.entrySet().stream().filter
                    (entry -> entry.getKey().toString().startsWith(keyPrefix)).
                    map(entry -> entry.getValue().toString()).collect(Collectors.toList()));
        } else
            throw new PropertyManagerException("Properties are empty");
        return propertyValues;
    }

    public Set<String> getPropertyValues() throws PropertyManagerException {
        Set<String> propertyValues = new HashSet<>();
        if (!properties.isEmpty()) {
            Set<Map.Entry<Object, Object>> entries = properties.entrySet();
            propertyValues.addAll(entries.stream().map(
                    entry -> entry.getValue().toString()).collect(Collectors.toList()));
        } else
            throw new PropertyManagerException("Properties are empty");
        return propertyValues;
    }

    public Properties getProperties() {
        return properties;
    }

}
