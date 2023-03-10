package com.example.uploadfileproject.controller;

import com.example.uploadfileproject.entity.Attachment;
import com.example.uploadfileproject.repository.AttachmentRepo;
import com.example.uploadfileproject.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentRepo attachmentRepo;
    @GetMapping("/test")
    public String test() {
        return "Works";
    }

    @PostMapping("/upload")
    public ResponseEntity<Attachment> upload(@RequestParam("file")MultipartFile file) throws Exception {
        Attachment attachment = attachmentService.upload(file);
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(attachment.getId().toString()).toUriString();
        attachment.setFileName(downloadUrl);
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(attachment);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer id) {
        // Load file from database
        Attachment file = attachmentRepo.findById(id).orElseThrow(() -> new RuntimeException("File not found"));

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName());
        headers.add(HttpHeaders.CONTENT_TYPE, file.getFileType());

        // Create resource
        ByteArrayResource resource = new ByteArrayResource(file.getFileBytes());

        // Return response entity
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.getFileBytes().length)
                .body(resource);
    }




    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable int id) {
        Optional<Attachment> fileOptional = attachmentRepo.findById(id);
        if (fileOptional.isPresent()) {
            Attachment fileEntity = fileOptional.get();
            HttpHeaders headers = new HttpHeaders();
            if (fileEntity.getFileType().equalsIgnoreCase("image/jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (fileEntity.getFileType().equalsIgnoreCase("image/png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (fileEntity.getFileType().equalsIgnoreCase("image/gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            }
            return ResponseEntity.ok()
                    .headers(headers)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileEntity.getFileName() + "\"")
                    .body(fileEntity.getFileBytes());
        } else {
            return ResponseEntity.notFound().build();
        }
    }





}
