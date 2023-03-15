package robbegarm.blogapi.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import robbegarm.blogapi.exception.PostNotFoundException;
import robbegarm.blogapi.exception.WrongRequestException;
import robbegarm.blogapi.file.FileService;
import robbegarm.blogapi.file.InputFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path="api/")
public class PostController {

    private final PostService postService;

    private final FileService fileService;

    @Autowired
    public PostController(PostService postService, FileService fileService){
        this.postService = postService;
        this.fileService = fileService;
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> getPosts (
            @RequestParam(value = "limit", required = false) Integer limitValue,
            @RequestParam(value = "offset", required = false) Integer offsetValue) throws SQLException, JsonProcessingException {
        String payload;
        payload = postService.fetchPosts(limitValue, offsetValue);
        return ResponseEntity.status(HttpStatus.OK).body(payload);
    } // Gets the list of all Posts in DB

    @GetMapping("/posts/{post_id}")
    public ResponseEntity<Object> getPostByID (@PathVariable String post_id) throws SQLException, JsonProcessingException, PostNotFoundException {
        String payload = postService.fetchPostByID(post_id);
        return ResponseEntity.status(HttpStatus.OK).body(payload);
    } // Gets a single Post by the specified post ID

    @PostMapping("/admin/posts")
    public ResponseEntity<Object> createPost (
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("image_name") String image_name,
            @RequestParam("image_alt") String image_alt,
            @RequestParam("files") MultipartFile[] files)
            throws SQLException, WrongRequestException { //Request Body will map to the parameter whatever is passed in the body
        Post post = new Post(
                null,  date, description, image_name, image_alt
        );
        postService.checkPostInput(post); //Throws an exception if the input for new post was invalid
        List<InputFile> inputFilesList = fileService.uploadFiles(files); //Adds the images to google bucket and returns the list
        String payload = postService.addPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(payload); // Returns Code 201 "CREATED" and sends post info
    } //Creates a new post with the specified properties, and appoints a unique post ID

    @PutMapping("/admin/posts/update-no-img/{post_id}")
    public ResponseEntity<Object> updatePostNoImg (
            @PathVariable String post_id,
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("image_alt") String image_alt)
            throws SQLException, WrongRequestException {
        Post post = new Post(
                null,  date, description, null, image_alt
        );
        String payload = postService.updatePostNoImg(post_id, post);
        return ResponseEntity.status(HttpStatus.OK).body(payload);// Returns Code 200 "OK"
    } //Updates the post by the specified post ID, without changing images

    @PutMapping("/admin/posts/update-with-img/{post_id}")
    public ResponseEntity<Object> updatePostWithImg (
            @PathVariable String post_id,
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("image_alt") String image_alt,
            @RequestParam("image_name") String image_name,
            @RequestParam("files") MultipartFile[] files)
            throws SQLException, WrongRequestException {
        Post post = new Post(
                null,  date, description, image_name, image_alt
        );
        postService.checkPostInput(post); //Throws an exception if the input for new post was invalid
        String oldImageName = postService.getPostImageName(post_id);
        String payload = postService.updatePostWithImg(post_id, post);
        List<InputFile> inputFilesList = fileService.uploadFiles(files); //Adds the images to google bucket and returns the list
        String fileServicePayload = fileService.deleteImages(oldImageName);
        return ResponseEntity.status(HttpStatus.OK).body(payload + fileServicePayload);// Returns Code 200 "OK"
    } //Updates the post by the specified post ID, without changing images


    @DeleteMapping("/admin/posts/{post_id}")
    public ResponseEntity<Object> deletePostByID (@PathVariable String post_id)
            throws SQLException, JsonProcessingException, WrongRequestException {
        String deletedPostImageName = postService.removePost(post_id);
        String fileServicePayload = fileService.deleteImages(deletedPostImageName);
        String payload = "201: The post with the image name of '" + deletedPostImageName +
                "' was successfully deleted from the database!. " + fileServicePayload;
        return ResponseEntity.status(HttpStatus.OK).body(payload);// Returns Code 200 "OK"
    } // Deletes a post by the specified post ID from the DB, and the corresponding images from the google cloud storage
}
