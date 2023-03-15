package robbegarm.blogapi.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import robbegarm.blogapi.config.DatabaseConnection;
import robbegarm.blogapi.exception.FileWriteException;
import robbegarm.blogapi.exception.GCPFileUploadException;
import robbegarm.blogapi.exception.InvalidTypeException;
import robbegarm.blogapi.exception.WrongRequestException;
import robbegarm.blogapi.file.FileDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataBucketUtil {
    Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    public FileDto uploadFile (MultipartFile multipartFile, String fileName, String contentType) {
        try{
            logger.debug("Start file uploading process on GCS");
//            System.err.println("Start file uploading process on GCS");
            byte[] fileData = FileUtils.readFileToByteArray(convertFile(multipartFile));
            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();
            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
            Storage storage = options.getService();
            Bucket bucket = storage.get(gcpBucketId,Storage.BucketGetOption.fields());
            String extension = checkFileExtension(fileName);
//            System.err.println("filename: " + fileName);
//            System.err.println("extension: " + extension);
            Blob blob = bucket.create(gcpDirectoryName + "/" + fileName, fileData, contentType);

            if(blob != null){
                logger.debug("File successfully uploaded to GCS");
//                System.err.println("File successfully uploaded to GCS");
                return new FileDto(blob.getName(), blob.getMediaLink());
            }

        }catch (Exception e){
            logger.error("An error occurred while uploading data. Exception: ", e);
//            System.err.println("An error occurred while uploading data. Exception: " + e);
            throw new GCPFileUploadException("An error occurred while storing data to GCS");
        }
        throw new GCPFileUploadException("An error occurred while storing data to GCS");
    }

    public String deleteImages (String img_name){
        try{
            logger.debug("Start file deleting process on GCS");
            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();
            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
            Storage storage = options.getService();
            boolean deleted1 = storage.delete(gcpBucketId, gcpDirectoryName + "/" + img_name + ".jpg");
            boolean deleted2 = storage.delete(gcpBucketId, gcpDirectoryName + "/" + img_name + ".webp");
            return "Object '" + img_name + ".jpg' was " + (deleted1 ? "successfully" : "not") + " deleted from " + gcpBucketId
                    + " and object '" + img_name + ".webp' was " + (deleted2 ? "successfully" : "not") + " deleted from " + gcpBucketId;
        }catch (Exception e){
            logger.error("An error occurred while deleting data. Exception: ", e);
//            System.err.println("An error occurred while deleting data. Exception: " + e);
            throw new GCPFileUploadException("An error occurred while deleting data from GCS");
        }
    }

    private File convertFile(MultipartFile file) {

        try{
            if(file.getOriginalFilename() == null){
                throw new WrongRequestException("Original file name is null");
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            logger.debug("Converting multipart file : {}", convertedFile);
//            System.err.println("Converting multipart file : " + convertedFile);
            return convertedFile;
        }catch (Exception e){
            throw new FileWriteException("An error has occurred while converting the file");
        }
    }

    private String checkFileExtension(String fileName) {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".jpg", ".jpeg", ".webp"};

            for(String extension: extensionList) {
                if (fileName.endsWith(extension)) {
                    logger.debug("Accepted file type : {}", extension);
//                    System.err.println("Accepted file type : " + extension);
                    return extension;
                }
            }
        }
        logger.error("Not a permitted file type");
//        System.err.println("Not a permitted file type");
        throw new InvalidTypeException("Not a permitted file type");
    }
}
