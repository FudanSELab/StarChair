package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LBW
 */
@RestController
public class AuthController {
    private AuthService authService;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        logger.debug("RegistrationForm: " + request.toString());
        return ResponseEntity.ok(authService.register(request)); //返回新注册的用户
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        logger.debug("LoginForm: " + request.toString());
        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }

    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to 2020 Software Engineering Lab2. ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

}



