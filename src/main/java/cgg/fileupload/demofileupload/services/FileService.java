package cgg.fileupload.demofileupload.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public String uploadimage(String path, MultipartFile m1) throws IOException;

    public InputStream getResource(String path, String filename) throws IOException;

}
