package ramsay.health.outlook.firebase;

import io.micronaut.http.HttpRequest;
import ramsay.health.outlook.feedback.UserFeedbackRequest;
import ramsay.health.shared.Response;
import ramsay.health.shared.Result;

public sealed interface IFirestoreService permits FirestoreService {
    Result<Response<UserFeedbackRequest>> create(UserFeedbackRequest userFeedbackRequest, HttpRequest<?> httpRequest);
}
