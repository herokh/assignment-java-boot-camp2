package com.javabootcamp.assessment2;

import com.javabootcamp.assessment2.entities.User;
import lombok.Getter;

public class ApplicationContext {

    private @Getter
    User currentUser;

    public void setCurrentUserIfNotExists(User currentUser) {
        if (this.currentUser == null) {
            this.currentUser = currentUser;
        }
    }

}
