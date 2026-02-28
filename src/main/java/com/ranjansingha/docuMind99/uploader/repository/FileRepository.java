package com.ranjansingha.docuMind99.uploader.repository;

import com.ranjansingha.docuMind99.uploader.entity.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileMetaData, Long> {
}
