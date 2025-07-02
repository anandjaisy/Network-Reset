package ramsay.health.outlook.firebase;

import com.google.cloud.firestore.*;
import io.micronaut.http.HttpRequest;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import jakarta.inject.Singleton;
import ramsay.health.IUserClaimsService;
import ramsay.health.outlook.companion.EmailSummaryService;
import ramsay.health.outlook.feedback.FeedbackType;
import ramsay.health.outlook.feedback.UserFeedbackRequest;
import ramsay.health.shared.MetaResponse;
import ramsay.health.shared.Response;
import ramsay.health.shared.Result;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Singleton
public record FirestoreService(Firestore firestore, IUserClaimsService iUserClaimsService) implements IFirestoreService {
    private static final Logger LOG = Logger.getLogger(FirestoreService.class.getName());
    @Override
    public Result<Response<UserFeedbackRequest>> create(UserFeedbackRequest request, HttpRequest<?> httpRequest) {
        // Flattened doc ID
        String requestId =  request.requestId();

        // Store all feedback in the same top-level collection
        DocumentReference docRef = firestore.collection("companion-plus-feedback").document(requestId);

        Map<String, Object> data = new HashMap<>();
        data.put("RequestId", requestId);
        data.put("ItemId", request.itemId());
        data.put("UserId", iUserClaimsService.getUserId());
        data.put("Email", iUserClaimsService.getEmail());
        data.put("ThumbUp", request.thumbUp());
        data.put("ThumbDown", request.thumbDown());
        data.put("Feedback", request.feedback());
        data.put("CreatedDate", Instant.now().toString());
        data.put("Type", request.feedbackType().name);

        try {
            var result = docRef.set(data);
            var writtenResponse = result.get();
            LOG.info("Write result updated time : " + writtenResponse.getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            LOG.severe(e.getMessage());
            return Result.of(new Exception(e));
        }
        return Result.of(new Response<>(request, MetaResponse.Of(httpRequest)));
    }
}
