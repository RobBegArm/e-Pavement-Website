package robbegarm.blogapi.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jnr.ffi.annotations.In;
import org.springframework.stereotype.Service;
import robbegarm.blogapi.config.DatabaseConnection;
import robbegarm.blogapi.exception.PostNotFoundException;
import robbegarm.blogapi.exception.WrongRequestException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostService {
    private final Connection dbCon;

    public PostService(DatabaseConnection connection) {
        this.dbCon = connection.getConnectionToDatabase();
    }

    public String fetchPosts (Integer LIMIT, Integer OFFSET) throws SQLException, JsonProcessingException{
        String queryCondition = (LIMIT != null && OFFSET != null) ? (
                " LIMIT " + LIMIT.toString() + " OFFSET " + OFFSET.toString()) : "";
        ResultSet resultSet = executeSQLQuery(
                """
                SELECT *
                FROM posts_data
                ORDER BY post_date DESC""" + queryCondition + ";");
        return AllPostsToJson(resultSet); //Executes query and converts result to JSON
    }

    public String fetchPostByID (String post_id) throws SQLException, JsonProcessingException{
        ResultSet resultSet = executeSQLQuery("""
                SELECT *
                FROM posts_data
                WHERE post_uid ='""" + post_id +"';");
        String payload = PostToJson(resultSet); //Executes query and converts result to JSON
        checkPostNotFound(payload, post_id); //Throws an exception if Post was not found
        return payload; //Executes query and converts result to JSON
    }

    public String addPost (Post post) throws SQLException, WrongRequestException{
        String newPostID = getNewUUID(); //Gets a new unique post ID from DB
        Post newPost = new Post(newPostID, post.getDate(), post.getDescription(), post.getImage_name(), post.getImage_alt());
        executeSQLUpdate("""
                INSERT INTO posts_data
                VALUES ('"""
                + newPost.getId() + "', '"
                + newPost.getDate() + "', '"
                + newPost.getDescription() + "', '"
                + newPost.getImage_name() + "', '"
                + newPost.getImage_alt() + "');");
        return "201: The post with date '" + newPost.getDate() + "' "
                + "was successfully created!"; //Executes update and converts result to JSON
    }

    public String updatePostNoImg (String post_id, Post updatedPost)
            throws SQLException, WrongRequestException{
        ResultSet resultSet = executeSQLQuery("""
                SELECT *
                FROM posts_data
                WHERE post_uid ='""" + post_id +"';");
        Post currentPost = getPostFromResultSet(resultSet); //Executes query and converts result to JSON
        String result = currentPost.toJson();
        checkPostNotFound(result, post_id); //Throws an exception if post with specified ID was not found
        checkUpdatePostInput(updatedPost); //Throws an exception if post update request is invalid
        executeSQLUpdate("""
                UPDATE posts_data
                SET
                post_date = '""" + updatedPost.getDate() + "', " +
                "post_description = '" + updatedPost.getDescription() + "', " +
                "post_image_name = '" + currentPost.getImage_name() + "', " +
                "post_image_alt = '" + updatedPost.getImage_alt() + "' " +
                "WHERE post_uid = '" + post_id + "';");
        return ("200: The post with date '" + updatedPost.getDate() + "' was successfully updated!");
    }

    public String updatePostWithImg (String post_id, Post updatedPost)
            throws SQLException, WrongRequestException{
        ResultSet resultSet = executeSQLQuery("""
                SELECT *
                FROM posts_data
                WHERE post_uid ='""" + post_id +"';");
        Post currentPost = getPostFromResultSet(resultSet); //Executes query and converts result to JSON
        String result = currentPost.toJson();
        checkPostNotFound(result, post_id); //Throws an exception if post with specified ID was not found
        checkUpdatePostInput(updatedPost); //Throws an exception if post update request is invalid
        executeSQLUpdate("""
                UPDATE posts_data
                SET
                post_date = '""" + updatedPost.getDate() + "', " +
                "post_description = '" + updatedPost.getDescription() + "', " +
                "post_image_name = '" + updatedPost.getImage_name() + "', " +
                "post_image_alt = '" + updatedPost.getImage_alt() + "' " +
                "WHERE post_uid = '" + post_id + "';");
        return ("200: The post with date '" + updatedPost.getDate() + "' was successfully updated!");
    }

    public String removePost (String post_id)
            throws SQLException, JsonProcessingException, WrongRequestException {
        ResultSet resultSet = executeSQLQuery("""
                SELECT *
                FROM posts_data
                WHERE post_uid ='""" + post_id +"';");
        Post currentPost = getPostFromResultSet(resultSet); //Executes query and converts result to JSON
        String result = currentPost.toJson(); //Executes query and converts result to JSON
        checkPostNotFound(result, post_id); //Throws an exception if post was not found
        executeSQLUpdate("""
                DELETE FROM posts_data
                WHERE
                post_uid = \"""" + post_id + "\";");
        return currentPost.getImage_name(); //Returns the deleted post's image name
    }

    public String getPostImageName (String post_id) throws SQLException {
        ResultSet resultSet = executeSQLQuery("""
                SELECT *
                FROM posts_data
                WHERE post_uid ='""" + post_id +"';");
        Post currentPost = getPostFromResultSet(resultSet); //Executes query and converts result to JSON
        return currentPost.getImage_name();
    }

    public ResultSet executeSQLQuery (String query ) throws SQLException {
        PreparedStatement ps = dbCon.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return ps.executeQuery();
    } // Executes MySQL query and returns ResultSet

    public void executeSQLUpdate ( String update ) throws SQLException {
        PreparedStatement ps = dbCon.prepareStatement(update, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ps.executeUpdate();
    } // Executes MySQL update

    public String AllPostsToJson ( ResultSet queryResult) throws SQLException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> postsList = new ArrayList<>();
        while (queryResult.next()) {
            Post post = new Post(
                    queryResult.getString("post_uid"),
                    queryResult.getString("post_date"),
                    queryResult.getString("post_description"),
                    queryResult.getString("post_image_name"),
                    queryResult.getString("post_image_alt"));
            postsList.add(post);
        }
        return objectMapper.writeValueAsString(postsList);
    } // Returns all Posts' info as a JSON String

    public String PostToJson ( ResultSet queryResult) throws SQLException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Post post = null;
        while (queryResult.next()) {
            post = new Post(
                    queryResult.getString("post_uid"),
                    queryResult.getString("post_date"),
                    queryResult.getString("post_description"),
                    queryResult.getString("post_image_name"),
                    queryResult.getString("post_image_alt"));
        }
        return objectMapper.writeValueAsString(post);
    } // Returns a single post info as a JSON String

    public Post getPostFromResultSet (ResultSet queryResult) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        Post post = null;
        while (queryResult.next()) {
            post = new Post(
                    queryResult.getString("post_uid"),
                    queryResult.getString("post_date"),
                    queryResult.getString("post_description"),
                    queryResult.getString("post_image_name"),
                    queryResult.getString("post_image_alt"));
        }
        return post;
    }

    public void checkPostNotFound (String payload, String post_id){
        if (Objects.equals(payload, "null" ) || Objects.equals(payload, "[]" ) ||payload.isEmpty()) { //Checks if the result is null
            throw new PostNotFoundException("Post with the ID '" + post_id + "' was not found "); // If NULL
        }
    } //Throws an exception if Post was not found

    public boolean checkDateInput (String input){
        final Pattern pattern = Pattern.compile("[1-2][0-9][0-9][0-9]-\\b(0[1-9]|1[0-2])\\b-\\b(0[1-9]|1[0-9]|2[0-9]|3[0-1])\\b", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }////Throws an exception if Post date format was wrong

    public void checkPostInput (Post post) {
        if (post.getId() != null){
            throw new WrongRequestException("No ID input is needed!");
        }
        if ( checkIfEmpty(post.getDate()) ){
            throw new WrongRequestException("Empty date!Please fill the date field.");
        }
        if ( !checkDateInput(post.getDate()) ){
            throw new WrongRequestException("Date has wrong format! Please use 'YYYY-MM-DD' format. Example: 2023-01-18");
        }
        if ( checkIfEmpty(post.getDescription()) ){
            throw new WrongRequestException("Empty description!Please fill the description field.");
        }
        if ( checkIfEmpty(post.getImage_name()) ){
            throw new WrongRequestException("Empty image name!Please fill the image name field.");
        }
        if ( checkIfEmpty(post.getImage_alt()) ){
            throw new WrongRequestException("Empty image alt!Please fill the image alt field.");
        }
    } //Throws an exception if post input for POST Create request is wrong

    public void checkUpdatePostInput (Post post) {
        if ( checkIfEmpty(post.getDate()) ){
            throw new WrongRequestException("Empty date!Please fill the date field.");
        }
        if ( !checkDateInput(post.getDate()) ){
            throw new WrongRequestException("Date has wrong format! Please use 'YYYY-MM-DD' format. Example: 2023-01-18");
        }
        if ( checkIfEmpty(post.getDescription()) ){
            throw new WrongRequestException("Empty description!Please fill the description field.");
        }
        if ( checkIfEmpty(post.getImage_alt()) ){
            throw new WrongRequestException("Empty image alt!Please fill the image alt field.");
        }
    } //Throws an exception if post input for PUT Update request is wrong


    public String getNewUUID (){
        return UUID.randomUUID().toString();
    } //Generates and returns a UUID

    public boolean checkIfEmpty (String parameter){
        return Objects.equals(parameter, " ") ||
                Objects.equals(parameter, "") ||
                Objects.equals(parameter, "[]") ||
                Objects.equals(parameter, "null") ||
                Objects.equals(parameter, null) ||
                parameter.isEmpty();
    }
}
