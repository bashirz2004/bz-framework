package com.bzamani.framework.config;

import org.springframework.web.multipart.MultipartFile;

public class Utility {

    public static String getMultiPartFileNameWithoutExtension(MultipartFile file) {
        int index = file.getOriginalFilename().lastIndexOf(".");
        return file.getOriginalFilename().substring(0,index);
    }

    public static String getMultiPartFileExtention(MultipartFile file) {
        int index = file.getOriginalFilename().lastIndexOf(".");
        return file.getOriginalFilename().substring(index + 1);
    }
}
