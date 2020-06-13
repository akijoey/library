package com.akijoey.library.util;

import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
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

    public void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public String uploadImage(MultipartFile image) {
        String path = ClassUtils.getDefaultClassLoader().getResource("static/img/").getPath();
        String name = uploadFile(path, image);
        return name == null ? null : "/img/" + name;
    }

    public void deleteImage(String src) {
        String path = ClassUtils.getDefaultClassLoader().getResource("static/img/").getPath();
        String name = src.replace("/img/", "");
        deleteFile(path + name);
    }

}