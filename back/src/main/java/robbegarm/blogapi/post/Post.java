package robbegarm.blogapi.post;

public class Post {
    private String id;
    private String date;
    private String description;
    private String image_name;
    private String image_alt;

    public Post() {
    }

    public Post(String id, String date, String description, String image_name, String image_alt) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.image_name = image_name;
        this.image_alt = image_alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getImage_alt() {
        return image_alt;
    }

    public void setImage_alt(String image_alt) {
        this.image_alt = image_alt;
    }

    public String toJson (){
        return "{\"post_uid\": \"" + this.id + "\"," +
                "\"post_date\":\"" + this.date + "\"," +
                "\"post_description\":\"" + this.description + "\"," +
                "\"post_image_name\":\"" + this.image_name + "\"," +
                "\"post_image_alt\":\"" + this.image_alt + "\"}";
    }
}
