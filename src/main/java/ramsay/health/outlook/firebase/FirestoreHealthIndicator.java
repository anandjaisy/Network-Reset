//package ramsay.health.outlook.firebase;
//
//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.DocumentReference;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.google.cloud.firestore.Firestore;
//import io.micronaut.health.HealthStatus;
//import io.micronaut.management.health.indicator.HealthIndicator;
//import io.micronaut.management.health.indicator.HealthResult;
//import io.micronaut.management.health.indicator.annotation.Liveness;
//import jakarta.inject.Singleton;
//import org.reactivestreams.Publisher;
//
//import java.util.Map;
//
//@Singleton
//@Liveness
//public class FirestoreHealthIndicator implements HealthIndicator {
//
//    private final Firestore firestore;
//
//    public FirestoreHealthIndicator(Firestore firestore) {
//        this.firestore = firestore;
//    }
//
//    @Override
//    public Publisher<HealthResult> getResult() {
//        return subscriber -> {
//            try {
//                DocumentReference docRef = firestore.collection("health-check").document("ping");
//                ApiFuture<DocumentSnapshot> future = docRef.get();
//                DocumentSnapshot document = future.get();
//                boolean exists = document.exists();
//
//                HealthResult result = HealthResult.builder("firestore")
//                        .status(HealthStatus.UP)
//                        .details(Map.of("documentFound", exists))
//                        .build();
//
//                subscriber.onNext(result);
//                subscriber.onComplete();
//            } catch (Exception e) {
//                HealthResult result = HealthResult.builder("firestore")
//                        .status(HealthStatus.DOWN)
//                        .details(Map.of("error", e.getMessage()))
//                        .build();
//
//                subscriber.onNext(result);
//                subscriber.onComplete();
//            }
//        };
//    }
//}