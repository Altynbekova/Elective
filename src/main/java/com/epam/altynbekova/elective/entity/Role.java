package com.epam.altynbekova.elective.entity;

import java.text.MessageFormat;

public enum Role {
    LECTURER("LECTURER"),
    STUDENT("STUDENT");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role getByName(String roleName){
        for (Role role : Role.values()) {
            if (roleName!=null&&role.getValue().equalsIgnoreCase(roleName))
                return role;
        }
        throw new IllegalArgumentException(MessageFormat.format("There is no role with name {0}",roleName));
    }
}