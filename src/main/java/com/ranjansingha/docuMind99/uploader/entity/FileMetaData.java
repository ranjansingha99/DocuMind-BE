package com.ranjansingha.docuMind99.uploader.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Data
public class FileMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;
    private Long size;
    private String objectName;
    private LocalDateTime uploadedAt;

    // getters & setters
}
