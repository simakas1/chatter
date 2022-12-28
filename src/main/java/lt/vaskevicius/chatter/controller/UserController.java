package lt.vaskevicius.chatter.controller;

import lt.vaskevicius.chatter.domain.dto.request.LoginRequest;
import lt.vaskevicius.chatter.domain.dto.request.RegisterRequest;
import lt.vaskevicius.chatter.domain.dto.response.AccessTokenResponse;
import lt.vaskevicius.chatter.domain.entity.User;
import lt.vaskevicius.chatter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public AccessTokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }

    @PostMapping("register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User register(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        userService.delete(id);
    }

    @GetMapping("whoami")
    public User whoAmI(HttpServletRequest request) {
        return userService.getUser(request);
    }

    @PostMapping("refresh")
    public AccessTokenResponse refreshToken(HttpServletRequest request) {
        return userService.refreshToken(request);
    }
}
