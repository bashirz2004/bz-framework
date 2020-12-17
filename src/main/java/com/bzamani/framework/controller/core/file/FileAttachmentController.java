package com.bzamani.framework.controller.core.file;

import com.bzamani.framework.common.utility.Utility;
import com.bzamani.framework.model.core.file.FileAttachment;
import com.bzamani.framework.service.core.file.IFileAttachmentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/rest/core/file", produces = "application/json;charset=UTF-8")
public class FileAttachmentController {
    @Autowired
    IFileAttachmentService iFileAttachmentService;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("attachment") MultipartFile attachment) throws IOException {
        byte[] fileBytes = attachment.getBytes();
        FileAttachment file = new FileAttachment();
        file.setAttachment(fileBytes);
        file.setFileName(Utility.getMultiPartFileNameWithoutExtension(attachment));
        file.setExtension(Utility.getMultiPartFileExtention(attachment));
        file.setFileCode(RandomStringUtils.random(20, true, true));
        iFileAttachmentService.save(file);
        return file.getFileCode();
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iFileAttachmentService.deleteByEntityId(id);
    }

    @GetMapping("/download/{fileCode}/{isForDownload}")
    public void download(@PathVariable("fileCode") String fileCode, @PathVariable("isForDownload") Boolean isForDownload, HttpServletResponse response) {
        writeAttachmentToResponse(fileCode, response, isForDownload);
    }

    private void writeAttachmentToResponse(String fileCode, HttpServletResponse response, Boolean isForDownload) {
        FileAttachment fileAttachment = iFileAttachmentService.getFileAttachmentByFileCode(fileCode);
        if (fileAttachment != null) {
            byte[] attachment = fileAttachment.getAttachment();
            String filname = fileAttachment.getFileName();
            byte[] img = null;
            if (attachment != null) {
                img = attachment;
            } else {
                try {
                    response.sendRedirect("/notFound");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String fileExtention = fileAttachment.getExtension();
            String contentType = "";
            if (fileExtention.equalsIgnoreCase("txt")) {
                contentType = "text/plain";
            } else if (fileExtention.equalsIgnoreCase("jpg") || fileExtention.equalsIgnoreCase("jpeg")) {
                contentType = "image/jpeg";
            } else if (fileExtention.equalsIgnoreCase("png") || fileExtention.equalsIgnoreCase("gif") || fileExtention.equalsIgnoreCase("bmp") || fileExtention.equalsIgnoreCase("tiff")) {
                contentType = "image/" + fileExtention;
            } else if (fileExtention.equalsIgnoreCase("pdf")) {
                contentType = "application/pdf";
            } else if (fileExtention.equalsIgnoreCase("xlsx") || fileExtention.equalsIgnoreCase("xls")) {
                contentType = "application/vnd.ms-excel";
            } else if (fileExtention.equalsIgnoreCase("docx") || fileExtention.equalsIgnoreCase("doc")) {
                contentType = "application/msword";
            } else if (fileExtention.equalsIgnoreCase("ppt") || fileExtention.equalsIgnoreCase("pps")) {
                contentType = "Application/vnd.ms-powerpoint";
            } else if (fileExtention.equalsIgnoreCase("zip")) {
                contentType = "application/x-zip-compressed";
            } else
                contentType = "other";

            response.setContentType(contentType + ";charset=UTF-8");
            if (isForDownload)
                response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + filname + "." + fileExtention);
            else
                response.setHeader("Content-Disposition", "inline; filename*=UTF-8''" + filname + "." + fileExtention);
            response.setContentLength(img.length);
            response.setHeader("Cache-Control", " max-age=0, -mustrevalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            ServletOutputStream ouputStream;
            try {
                ouputStream = response.getOutputStream();
                ouputStream.write(img, 0, img.length);
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

}
