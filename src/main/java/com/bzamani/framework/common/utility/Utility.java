package com.bzamani.framework.common.utility;

import com.bzamani.framework.dto.UserNewPasswordSMSDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        UserNewPasswordSMSDto dto = new UserNewPasswordSMSDto("+983000505", receipants, text);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "AccessKey JucDHH6f4nXGwryx3RRvmTOjfBz-7hgDi7UkyHJ5mYk=");

        HttpEntity<UserNewPasswordSMSDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        return String.valueOf(result.getStatusCodeValue());
    }

    public static String sendSMSWithPatternAfterRefer(String receiverMobile, String firstname, String lastname, String clinic, String address, String id, String telephone) throws URISyntaxException, UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://ippanel.com:8080/?apikey=JucDHH6f4nXGwryx3RRvmTOjfBz-7hgDi7UkyHJ5mYk=&pid=guf0jzyfu5&fnum=3000505&tnum=" + receiverMobile
                + "&p1=firstname&p2=lastname&p3=clinic&p4=address&p5=id&p6=telephone&v1=" +
                URLEncoder.encode(firstname, StandardCharsets.UTF_8.toString()) + "&v2=" +
                URLEncoder.encode(lastname, StandardCharsets.UTF_8.toString()) + "&v3=" +
                URLEncoder.encode(clinic, StandardCharsets.UTF_8.toString()) + "&v4=" +
                URLEncoder.encode(address, StandardCharsets.UTF_8.toString()) + "&v5=" +
                URLEncoder.encode(id, StandardCharsets.UTF_8.toString()) + "&v6=" +
                URLEncoder.encode(telephone, StandardCharsets.UTF_8.toString());
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        return String.valueOf(result.getStatusCodeValue());
    }
}
