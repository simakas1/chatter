package lt.vaskevicius.chatter.service;

import lt.vaskevicius.chatter.domain.dto.request.LoginRequest;
import lt.vaskevicius.chatter.domain.dto.request.RegisterRequest;
import lt.vaskevicius.chatter.domain.dto.response.AccessTokenResponse;
import lt.vaskevicius.chatter.domain.entity.User;
import lt.vaskevicius.chatter.domain.exception.ChatterException;
import lt.vaskevicius.chatter.domain.exception.ChatterExceptionCode;
import lt.vaskevicius.chatter.repository.UserRepository;
import lt.vaskevicius.chatter.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ChatterException(ChatterExceptionCode.CHAT4, String.format("User with id: %s not found", userId)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public AccessTokenResponse authenticate(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new ChatterException(ChatterExceptionCode.CHAT4, String.format("User with username: %s not found", loginRequest.getUsername())));

            String accessToken = tokenProvider.createToken(loginRequest.getUsername(), user.getRole());

            return AccessTokenResponse.fromString(accessToken);

        } catch (AuthenticationException e) {
            throw new ChatterException(ChatterExceptionCode.CHAT2, "Invalid username or password");
        }
    }

    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ChatterException(ChatterExceptionCode.CHAT2, "User already exist");
        }

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getUserRole());

        return userRepository.save(user);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User getUser(HttpServletRequest req) {
        return userRepository.findByUsername(tokenProvider.getUsername(tokenProvider.resolveToken(req)))
                .orElseThrow(() -> new ChatterException(ChatterExceptionCode.CHAT4, "User not found"));
    }


    public AccessTokenResponse refreshToken(HttpServletRequest request) {
        User user = getUser(request);

        return AccessTokenResponse.fromString(tokenProvider.createToken(user.getUsername(), user.getRole()));
    }
}
