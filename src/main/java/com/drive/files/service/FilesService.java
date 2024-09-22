package com.drive.files.service;

import com.drive.clients.google.drive.GoogleDriveOAuthClientBuilder;
import com.drive.clients.google.drive.GoogleDriveServiceClientBuilder;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilesService {

    private final GoogleDriveOAuthClientBuilder googleDriveOAuthClientBuilder;
    private final GoogleDriveServiceClientBuilder googleDriveServiceClientBuilder;

    public List<File> listFilesUsingOauthClient() {
        return listFiles(googleDriveOAuthClientBuilder.buildGoogleDriveOAuthClient());
    }

    public List<File> listFilesUsingServiceClient() {
        return listFiles(googleDriveServiceClientBuilder.buildGoogleDriveServiceClient());
    }

    public List<File> listFiles(Drive driveClient) {
        List<File> files = new ArrayList<>();
        String pageToken = null;
        try {
            do {
                FileList result = driveClient.files().list()
                        .setSpaces("drive")
                        .setPageToken(pageToken)
                        .execute();
                for (File file : result.getFiles()) {
                    log.info("Found file: {} ({}) %n",
                            file.getName(), file.getId());
                }
                files.addAll(result.getFiles());

                pageToken = result.getNextPageToken();
            } while (pageToken != null);
        } catch (IOException exception) {
            log.error("Cannot list files: {}", exception.getMessage());
        }
        return files;
    }
}
