package robbegarm.blogapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping(path="api/user/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/data")
    public ResponseEntity<Object> getCurrentUserData() throws SQLException {
        String payload = userService.getCurrentUserData();
        return ResponseEntity.ok(payload);
    }

    @PutMapping("/email")
    public ResponseEntity<Object> changeEmail(
            @RequestParam ("email") String email,
            @RequestParam ("newEmail") String newEmail
    ) throws SQLException {
        String payload = userService.changeEmail(email, newEmail);
        return ResponseEntity.ok(payload);
    }

    @PutMapping("/question")
    public ResponseEntity<Object> changeQuestion(
            @RequestParam ("email") String email,
            @RequestParam ("newQuestion") String newQuestion
    ) throws SQLException {
        String payload = userService.changeUserSecretQuestion(email, newQuestion);
        return ResponseEntity.ok(payload);
    }

    @PutMapping("/answer")
    public ResponseEntity<Object> changeAnswer(
            @RequestParam ("email") String email,
            @RequestParam ("newAnswer") String newAnswer
    ) throws SQLException {
        String payload = userService.changeUserSecretAnswer(email, newAnswer);
        return ResponseEntity.ok(payload);
    }

    @PutMapping("/password")
    public ResponseEntity<Object> changePassword(
            @RequestParam ("email") String email,
            @RequestParam ("newPassword") String newPassword
            ) throws SQLException {
        String payload = userService.changeUserPassword(email, newPassword);
        return ResponseEntity.ok(payload);
    }
}
