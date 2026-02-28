package com.ranjansingha.docuMind99.uploader.controller;

import com.ranjansingha.docuMind99.uploader.entity.FileMetaData;
import com.ranjansingha.docuMind99.uploader.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public FileMetaData uploadPdf(@RequestParam("file") MultipartFile file) throws Exception {
        return fileService.uploadPdf(file);
    }
}
