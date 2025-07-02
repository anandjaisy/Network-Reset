package ramsay.health.outlook;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.validation.Valid;
import ramsay.health.outlook.companion.IEmailSummaryService;
import ramsay.health.outlook.email.EmailSummaryRequest;
import ramsay.health.outlook.feedback.UserFeedbackRequest;
import ramsay.health.outlook.firebase.IFirestoreService;

@Controller("v1/outlook")
@ExecuteOn(TaskExecutors.BLOCKING)
public class OutlookController {
    private final IEmailSummaryService emailSummaryService;
    private final IFirestoreService firestoreService;

    public OutlookController(IEmailSummaryService emailSummaryService,
                             IFirestoreService firestoreService) {
        this.emailSummaryService = emailSummaryService;
        this.firestoreService = firestoreService;
    }

    @Post("/summary")
    public HttpResponse<?> summary(@Valid @Body EmailSummaryRequest request, HttpRequest<?> httpRequest) {
        var result = emailSummaryService.summerize(request, httpRequest);
        return result.match(success -> HttpResponse.ok(result.value), (error) -> HttpResponse.serverError(error));
    }

    @Post("/feedback")
    public HttpResponse<?> feedback(@Valid @Body UserFeedbackRequest request, HttpRequest<?> httpRequest) {
        var result = firestoreService.create(request,httpRequest);
        return result.match(success -> HttpResponse.ok(result.value), () -> HttpResponse.serverError());
    }
}