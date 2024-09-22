package com.drive.clients.google.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class GoogleDriveOAuthClientBuilder {

    private static final java.io.File DATA_STORE_DIR = new java.io.File("tokens");

    public Drive buildGoogleDriveOAuthClient() {
        return new Drive
                .Builder(
                createNetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                createCredentials())
                .setApplicationName("drive test app - oauth2 auth")
                .build();

    }

    private Credential createCredentials() {
        // Load client secrets.
        try (InputStream driveCredentials = getClass().getClassLoader().getResourceAsStream("account/keys/GoogleOAuth2AccountKey.json")) {
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new InputStreamReader(driveCredentials));

            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            createNetHttpTransport(), GsonFactory.getDefaultInstance(), clientSecrets, Set.of(DriveScopes.DRIVE))
                            .setDataStoreFactory(createDataStoreFactory())
                            .setAccessType("offline")
                            .build();

            Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                            .authorize("user");
            log.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
            return credential;
        } catch (IOException exception) {
            throw new RuntimeException("Cannot read credentials", exception);
        }
    }

    private NetHttpTransport createNetHttpTransport() {
        return new NetHttpTransport.Builder().build();
    }

    private FileDataStoreFactory createDataStoreFactory() throws IOException {
        return new FileDataStoreFactory(DATA_STORE_DIR);
    }
}
