package alcemirjunior.github.desafiotunts.integration.googleSheet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class GoogleSheetProvider {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = List.of(SheetsScopes.DRIVE, SheetsScopes.SPREADSHEETS);

    @Value("${application.name}")
    private String applicationName;
    @Value("${credentials.file.path}")
    private String credentialsFilePath;
    @Value("${token.directory.path}")
    private String tokenDirectoryPath;

    public GoogleSheetProvider() {
    }

    public Sheets getIntegration() throws GeneralSecurityException, IOException {
        return new Sheets
                .Builder(
                    getHttpTransport(),
                    JSON_FACTORY,
                    getCredentials()
                )
                .setApplicationName(applicationName)
                .build();
    }

    private Credential getCredentials() throws IOException, GeneralSecurityException {
        InputStream in = new FileInputStream(credentialsFilePath);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(
                    getHttpTransport(),
                    JSON_FACTORY,
                    clientSecrets,
                    SCOPES
                )
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokenDirectoryPath)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver)
                .authorize("493104512215-o02nvkct1iv8tflrkt722ce16on3pog4.apps.googleusercontent.com");
    }

    private static NetHttpTransport getHttpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }
}
