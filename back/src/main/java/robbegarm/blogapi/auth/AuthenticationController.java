package robbegarm.blogapi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import robbegarm.blogapi.user.User;
import robbegarm.blogapi.user.UserService;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    private final UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody RegisterRequest request
//    ) throws SQLException {
//        return ResponseEntity.ok(authService.register(request));
//    }

    @PostMapping("/question")
    public ResponseEntity<Object> getUserQuestion(@RequestParam("email") String email) throws SQLException {
        User foundUser = userService.getUserByEmail(email);
        String payload = foundUser.getSecret_question();
        return ResponseEntity.ok(payload);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) throws SQLException {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/recover")
    public ResponseEntity<Object> recover(
            @RequestParam ("email") String email,
            @RequestParam ("secret_answer") String secret_answer) throws SQLException {
        String payload = userService.recoverWithSecretAnswer(email, secret_answer);
        return ResponseEntity.ok(payload);
    }

}
