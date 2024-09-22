package com.drive.clients.google.drive;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class GoogleDriveServiceClientBuilder {

    public Drive buildGoogleDriveServiceClient() {
        return new Drive
                .Builder(
                createNetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(createGoogleCredentials()))
                .setApplicationName("drive test app - service auth")
                .build();

    }

    private NetHttpTransport createNetHttpTransport() {
        return new NetHttpTransport.Builder().build();
    }

    private GoogleCredentials createGoogleCredentials() {
        try (InputStream driveCredentials = getClass().getClassLoader().getResourceAsStream("account/keys/GoogleServiceAccountKey.json")) {
            return ServiceAccountCredentials
                    .fromStream(driveCredentials)
                    .createScoped(Set.of(DriveScopes.DRIVE));
        } catch (IOException exception) {
            throw new RuntimeException("Cannot read credentials", exception);
        }
    }
}
