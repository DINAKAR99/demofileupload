package cgg.fileupload.demofileupload.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadimage(String path, MultipartFile m1) throws IOException {

        String name = m1.getOriginalFilename();

        String string = UUID.randomUUID().toString();

        String filename1 = string.concat(name.substring(name.lastIndexOf(".")));

        String fullpath = path + File.separator + filename1;
        // create folder if not present

        File f1 = new File(path);

        if (!f1.exists()) {
            f1.mkdir();

            Files.copy(m1.getInputStream(), Paths.get(fullpath));

        }

        return name;

    }

    @Override
    public InputStream getResource(String path, String filename) throws IOException {

        String fullpath = path + File.separator + filename;

        FileInputStream f1 = new FileInputStream(fullpath);

        // db loigic

        return f1;

    }

}
