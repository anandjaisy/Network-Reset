package ramsay.health.outlook.companion;

import io.micronaut.http.HttpRequest;
import ramsay.health.outlook.email.EmailSummaryRequest;
import ramsay.health.outlook.email.EmailSummaryResponse;
import ramsay.health.shared.Response;
import ramsay.health.shared.Result;

public sealed interface IEmailSummaryService permits EmailSummaryService{
    Result<Response<EmailSummaryResponse>> summerize(EmailSummaryRequest request, HttpRequest<?> httpRequest);
}
