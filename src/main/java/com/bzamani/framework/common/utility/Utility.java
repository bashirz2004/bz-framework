package com.bzamani.framework.common.utility;

import com.bzamani.framework.dto.UserNewPasswordSMSDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

public class Utility {

    public static String getMultiPartFileNameWithoutExtension(MultipartFile file) {
        int index = file.getOriginalFilename().lastIndexOf(".");
        return file.getOriginalFilename().substring(0, index);
    }

    public static String getMultiPartFileExtention(MultipartFile file) {
        int index = file.getOriginalFilename().lastIndexOf(".");
        return file.getOriginalFilename().substring(index + 1);
    }

    public static String sendSMS(String receiverMobile, String text) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://rest.ippanel.com/v1/messages";
        URI uri = new URI(baseUrl);
        String[] receipants = new String[1];
        receipants[0] = "98" + receiverMobile.substring(1);
        UserNewPasswordSMSDto dto = new UserNewPasswordSMSDto("+98500010403843845", receipants, text);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "AccessKey JucDHH6f4nXGwryx3RRvmTOjfBz-7hgDi7UkyHJ5mYk=");

        HttpEntity<UserNewPasswordSMSDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        return String.valueOf(result.getStatusCodeValue());
    }
}
