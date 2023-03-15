package robbegarm.blogapi.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import robbegarm.blogapi.config.JwtService;
import robbegarm.blogapi.exception.InvalidLoginException;
import robbegarm.blogapi.config.DatabaseConnection;
import robbegarm.blogapi.exception.WrongRequestException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Service
public class UserService {
    private final Connection dbCon;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Value("${DB_NAME}")
    private String databaseName;

    @Lazy
    public UserService(DatabaseConnection connection, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.dbCon = connection.getConnectionToDatabase();
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResultSet executeSQLQuery (String query ) throws SQLException {
        PreparedStatement ps = dbCon.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return ps.executeQuery();
    } // Executes MySQL query and returns ResultSet

    public void executeSQLUpdate ( String update ) throws SQLException {
        PreparedStatement ps = dbCon.prepareStatement(update, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ps.executeUpdate();
    } // Executes MySQL update

    public User getUserFromResultSet (ResultSet resultSet)  throws SQLException{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        while (resultSet.next()) {
            user = new User(
                    resultSet.getString("user_id"),
                    resultSet.getString("user_email"),
                    resultSet.getString("user_password"),
                    resultSet.getString("user_secret_question"),
                    resultSet.getString("user_secret_answer"),
                    getUserRole(resultSet.getString("user_role")));
        }
        return user;
    }

    public Role getUserRole (String role){
        if (Objects.equals(role, "ADMIN")){
            return Role.ADMIN;
        }else {
            return Role.USER;
        }
    }

    public User getUserByEmail(String username) throws SQLException, InvalidLoginException, UsernameNotFoundException {
        ResultSet resultSet = executeSQLQuery(
                """
                SELECT *
                FROM\s""" + databaseName +  ".user_data " +
               "WHERE user_email ='" + username +"';");
        User foundUser = getUserFromResultSet(resultSet);
        if(foundUser == null){
            throw new InvalidLoginException("Username Not Found!");
        }
        return foundUser;
    }

    public String getCurrentUserData() throws SQLException {
        ResultSet resultSet = executeSQLQuery(
                """
                SELECT *
                FROM\s""" + databaseName +  ".user_data;");
        User foundUser = getUserFromResultSet(resultSet);
        if(foundUser == null){
            throw new InvalidLoginException("Username Not Found!");
        }
        return foundUser.toJson();
    }

    public String changeUserPassword (String email, String newPassword) throws SQLException {
        checkPasswordInput(email, newPassword);//Check input
        User currentUser = getUserByEmail(email);
        String encodedPassword = passwordEncoder.encode(newPassword);
        executeSQLUpdate("""
                UPDATE user_data
                SET
                user_password = '""" + encodedPassword + "' " +
                "WHERE user_id = '" + currentUser.getId() + "';");
        return "200: The password was successfully changed!";
    }

    public String changeUserSecretQuestion (String email, String question) throws SQLException {
        checkQuestionInput(question);//Check input
        User currentUser = getUserByEmail(email);
        executeSQLUpdate("""
                UPDATE user_data
                SET
                user_secret_question = '""" + question + "' " +
                "WHERE user_id = '" + currentUser.getId() + "';");
        return "200: The secret question was successfully changed!";
    }

    public String changeUserSecretAnswer (String email, String answer) throws SQLException {
        checkQuestionInput(answer);//Check input
        User currentUser = getUserByEmail(email);
        String encodedAnswer = passwordEncoder.encode(answer);
        executeSQLUpdate("""
                UPDATE user_data
                SET
                user_secret_answer = '""" + encodedAnswer + "' " +
                "WHERE user_id = '" + currentUser.getId() + "';");
        return "200: The secret answer was successfully changed!";
    }

    public String changeEmail (String email, String newEmail) throws SQLException{
        User currentUser = getUserByEmail(email);
        checkEmailInput(newEmail);
        executeSQLUpdate("""
                UPDATE user_data
                SET
                user_email = '""" + newEmail + "' " +
                "WHERE user_id = '" + currentUser.getId() + "';");
        var loggedInUser = getUserByEmail(newEmail);
        var jwtToken = jwtService.generateToken(loggedInUser);
        return "{\"message\": \"200: The email was successfully changed!\", \"token\": \"" + jwtToken + "\"}";
    }

    public String recoverWithSecretAnswer (String email, String secret_answer) throws SQLException {
        User foundUser = getUserByEmail(email);
        if (!passwordEncoder.matches(secret_answer, foundUser.getSecret_answer())){
            throw new InvalidLoginException("Wrong Secret Answer!");
        }
        var jwtToken = jwtService.generateToken(foundUser);
        return "{\"message\": \"200: The secret answer was correct!\", \"token\": \"" + jwtToken + "\"}";
    }

    public void checkEmailInput (String email){
        if ( checkIfEmpty(email)){
            throw new WrongRequestException("The input is empty!");
        }
        if ( email.length() < 3 || email.length() > 90){
            throw new WrongRequestException("The new email input should be between 3 and 90 characters long!");
        }
        if ( !email.contains("@")){
            throw new WrongRequestException("The new email input should contain @!");
        }
    }

    public void checkQuestionInput (String question){
        if ( checkIfEmpty(question)){
            throw new WrongRequestException("The input is empty!");
        }
        if ( question.length() < 3 || question.length() > 64){
            throw new WrongRequestException("The new secret question/answer input should be between 3 and 64 characters long!");
        }
    }

    public void checkPasswordInput (String email, String newPassword){
        if (checkIfEmpty(newPassword) ||
            checkIfEmpty(email)){
            throw new WrongRequestException("One of the inputs is empty!");
        }
        if (newPassword.length() < 8 || newPassword.length() > 64 ){
            throw new WrongRequestException("The new password input should be between 6 and 64 characters long!");
        }
        if (!containsUpperCaseAndLowerCase(newPassword)){
            throw new WrongRequestException("The new password must contain uppercase and lowercase letters!");
        }
    }

    public boolean containsUpperCaseAndLowerCase(String str) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;

        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }

            if (hasUpperCase && hasLowerCase) {
                return true;
            }
        }

        return false;
    }

    public boolean checkIfEmpty (String parameter){
        return Objects.equals(parameter, " ") ||
                Objects.equals(parameter, "") ||
                Objects.equals(parameter, "[]") ||
                Objects.equals(parameter, null) ||
                parameter.isEmpty();
    }
}
