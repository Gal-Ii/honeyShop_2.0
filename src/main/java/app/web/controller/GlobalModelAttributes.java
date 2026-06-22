package app.web.controller;

import app.service.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {
    private final UserService userService;

    public GlobalModelAttributes(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(){
        return userService.isAdmin();
    }
}
