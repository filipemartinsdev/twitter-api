package com.api.twitter.common.model;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER"), ADMIN("USER");

    private String name;

    private UserRole(String name){
        this.name = name;
    }
}
