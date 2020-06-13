package com.akijoey.library.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    public String uploadImage(MultipartFile file) {
        String path = "src/main/resources/static/img/";
        String name = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String suffix = name.substring(name.lastIndexOf("."));
        File image = new File(path + uuid + suffix);
        try {
            file.transferTo(image);
            return "/img/" + uuid + suffix;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}