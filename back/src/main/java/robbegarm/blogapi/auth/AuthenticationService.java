package robbegarm.blogapi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import robbegarm.blogapi.config.JwtService;
import robbegarm.blogapi.exception.InvalidLoginException;
import robbegarm.blogapi.user.Role;
import robbegarm.blogapi.user.User;
import robbegarm.blogapi.user.UserService;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Value("${DB_NAME}")
    private String databaseName;

    public AuthenticationResponse register (RegisterRequest request) throws SQLException, InvalidLoginException, UsernameNotFoundException {
        User newUser = new User(
                "USER_" + UUID.randomUUID().toString(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getSecret_question(),
                request.getSecret_answer(),
                Role.USER);
        String sqlUpdateString = """
                INSERT INTO\s""" + databaseName +  ".user_data VALUES ('"
                + newUser.getId() + "', '"
                + newUser.getUsername() + "', '"
                + newUser.getPassword() + "', '"
                + newUser.getRole() + "', '"
                + newUser.getSecret_question() + "', '"
                + newUser.getSecret_answer() + "');";
//        System.err.println(sqlUpdateString);
        userService.executeSQLUpdate(sqlUpdateString);
        var jwtToken = jwtService.generateToken(newUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate (AuthenticationRequest request) throws SQLException, InvalidLoginException, UsernameNotFoundException {
        User requestedUser = userService.getUserByEmail(request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        try {
            var loggedInUser = userService.getUserByEmail(request.getEmail());
            var jwtToken = jwtService.generateToken(loggedInUser);
            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .build();
        } catch (SQLException | InvalidLoginException | UsernameNotFoundException e) {
            throw new InvalidLoginException ("Wrong Login Credentials");
        }
    }

    public Boolean validateRegisterRequest ( RegisterRequest request){
        if (Objects.equals(request.getEmail(), "null" ) ||
                Objects.equals(request.getEmail(), "[]" ) ||
                request.getEmail().isEmpty() ||
                request.getEmail().length() < 6){
            return false;
        }
        if (Objects.equals(request.getPassword(), "null" ) ||
                Objects.equals(request.getPassword(), "[]" ) ||
                request.getPassword().isEmpty() ||
                request.getPassword().length() < 6){
            return false;
        }
        return true;
    }

}
