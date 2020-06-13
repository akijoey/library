package com.akijoey.library.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    public String uploadFile(String path, MultipartFile file) {
        String name = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String suffix = name.substring(name.lastIndexOf("."));
        File local = new File(path + uuid + suffix);
        if(!local.getParentFile().exists()) {
            local.getParentFile().mkdirs();
        }
        try {
            file.transferTo(local);
            return uuid + suffix;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}