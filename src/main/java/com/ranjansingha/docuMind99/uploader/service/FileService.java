package com.ranjansingha.docuMind99.uploader.service;

import com.ranjansingha.docuMind99.uploader.entity.FileMetaData;
import com.ranjansingha.docuMind99.uploader.repository.FileRepository;
import io.minio.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileService {

    private final MinioClient minioClient;
    private final FileRepository fileRepository;

    private final String bucketName = "pdf-bucket";

    public FileService(MinioClient minioClient, FileRepository fileRepository) {
        this.minioClient = minioClient;
        this.fileRepository = fileRepository;
    }

    public FileMetaData uploadPdf(MultipartFile file) throws Exception {

        if (!"application/pdf".equals(file.getContentType())) {
            throw new RuntimeException("Only PDF files allowed");
        }

        String objectName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Create bucket if not exists
        boolean found = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build());

        if (!found) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // Upload to MinIO
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        // Save metadata in DB
        FileMetaData metadata = new FileMetaData();
        metadata.setFileName(file.getOriginalFilename());
        metadata.setContentType(file.getContentType());
        metadata.setSize(file.getSize());
        metadata.setObjectName(objectName);
        metadata.setUploadedAt(LocalDateTime.now());

        return fileRepository.save(metadata);
    }
}
