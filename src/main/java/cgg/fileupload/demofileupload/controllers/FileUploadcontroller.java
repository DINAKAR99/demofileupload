package cgg.fileupload.demofileupload.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cgg.fileupload.demofileupload.payload.ApiResponse;
import cgg.fileupload.demofileupload.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/file")
public class FileUploadcontroller {

    @Autowired
    FileService f1;

    @Value("${project.images}")
    String path;
    public Logger logger = LoggerFactory.getLogger(FileUploadcontroller.class);
    //////////////

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadfile(@RequestParam("image") MultipartFile file) {

        String filename = null;
        try {

            System.out.println(file.getContentType());
            // validation

            if (file.isEmpty()) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(null, "image is not uploaded to errro"),
                        HttpStatus.BAD_REQUEST);

            }
            if (!file.getContentType().equals("image/png")) {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(null, "only pngs are allowed"));

            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(null, "only pngs are allowed"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(null, "image uploaded"));
    }

    @GetMapping(value = "/images/{imagename}", produces = MediaType.IMAGE_PNG_VALUE)
    public void downloadImage(@PathVariable String imagename, HttpServletResponse response) throws IOException {

        InputStream resource = this.f1.getResource(path, imagename);

        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

    }

    @PostMapping("/uploadfiles")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("images") MultipartFile[] file) {
        this.logger.info("{} number of files uploaded", file.length);

        return ResponseEntity.ok("file uploaded");
    }

}
