package com.example.uploadfileproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@NoArgsConstructor
@Table(name = "attachment")
public class Attachment {
    public Attachment(String fileName, String fileType, byte[] fileBytes) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileBytes = fileBytes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "file_bytes")
    private byte[] fileBytes;
}
