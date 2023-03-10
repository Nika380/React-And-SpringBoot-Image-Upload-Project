package com.example.uploadfileproject.service;

import com.example.uploadfileproject.entity.Attachment;
import com.example.uploadfileproject.repository.AttachmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepo attachmentRepo;


    public Attachment upload(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new Exception("File Contains invalid Path Sequence: " + fileName);
            }
            Attachment attachment = new Attachment(
                    fileName,
                    file.getContentType(),
                    file.getBytes()
            );
            return attachmentRepo.save(attachment);
        } catch (Exception e) {
            throw new Exception("Could not save file");
        }


    }

    public Attachment getAttachment(Integer fileId) throws Exception {
        return attachmentRepo
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
}
