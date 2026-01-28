package com.api.twitter.common.domainevent.events;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class CreateUserEvent extends ApplicationEvent {
    @NotEmpty
    private String username;

    @Email
    private String email;

    @NotEmpty
    private String encryptedPassword;

    public CreateUserEvent(String username, String email, String encryptedPassword, Object source) {
        super(source);
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

    public CreateUserEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
