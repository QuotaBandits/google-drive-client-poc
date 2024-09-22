package com.drive.files.controller;

import com.drive.files.service.FilesService;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class FilesController {

    private final FilesService filesService;

    @GetMapping("/listUsingOauthClient")
    public List<File> listFilesUsingOauthClient() {
        return filesService.listFilesUsingOauthClient();
    }

    @GetMapping("/listUsingServiceClient")
    public List<File> listFilesUsingServiceClient() {
        return filesService.listFilesUsingServiceClient();
    }
}
