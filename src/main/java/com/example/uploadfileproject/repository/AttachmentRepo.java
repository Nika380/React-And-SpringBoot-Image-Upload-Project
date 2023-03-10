package com.example.uploadfileproject.repository;

import com.example.uploadfileproject.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment,Integer> {
}
