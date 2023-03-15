package robbegarm.blogapi.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import robbegarm.blogapi.exception.GCPFileUploadException;
import robbegarm.blogapi.exception.WrongRequestException;
import robbegarm.blogapi.post.PostController;
import robbegarm.blogapi.util.DataBucketUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final DataBucketUtil dataBucketUtil;

    public FileService(DataBucketUtil dataBucketUtil) {
        this.dataBucketUtil = dataBucketUtil;
    }

    //public List<InputFile> uploadFiles (MultipartFile[] files) {
    public List<InputFile> uploadFiles (MultipartFile[] files) {
//        System.err.println("Going Into Service: uploadFiles()");
        logger.debug("Going Into Service: uploadFiles()");
        if(files.length != 2){
            throw new WrongRequestException("2 Images are necessary, .jpg and .webp");
        }
        List<InputFile> inputFiles = new ArrayList<>();
        Arrays.asList(files).forEach(file -> {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                throw new WrongRequestException("Original file name is null");
            }
            Path path = new File(originalFileName).toPath();
            try {
                String contentType = Files.probeContentType(path);
//                System.err.println(path + ":" + "with type:" + contentType);
                FileDto fileDto = dataBucketUtil.uploadFile(file, originalFileName, contentType);

                if (fileDto != null) {
                    inputFiles.add(new InputFile(fileDto.getFileName(), fileDto.getFileUrl()));
                    logger.debug("File uploaded successfully, file name: {} and url: {}",fileDto.getFileName(), fileDto.getFileUrl() );
                }
            } catch (Exception e) {
                logger.error("Error occurred while uploading. Error: ", e);
//                System.err.println("Error occurred while uploading. Error: " + e);
                throw new GCPFileUploadException("Error occurred while uploading");
            }
        });
//        System.err.println("Finished the adding to list: The list now:");
        logger.debug("Finished the adding to list: The list now:");
//        System.err.println(inputFiles);
        return inputFiles;
    }

    public String deleteImages (String img_name){
        String payload = dataBucketUtil.deleteImages(img_name);
//        System.err.println(payload);
        logger.debug(payload);
        return payload;
    }
}
