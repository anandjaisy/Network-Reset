package ramsay.health.outlook.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

import java.io.IOException;

@Factory
public class FirestoreFactory {
    @Singleton
    public Firestore firestore(GcpConfig gcpConfig) throws IOException {
        return FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(gcpConfig.project())
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build()
                .getService();
    }
}
