package ramsay.health.outlook.companion;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import ramsay.health.outlook.email.EmailSummaryResponse;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;

@Client(id = "companion")
@Header(name = CONTENT_TYPE, value = "application/json")
public interface ICompanionClient {
    @Retryable
    @Post("ramsay-companion-email/summarize")
    HttpResponse<EmailSummaryResponse> summerize(@Body CompanionRequest request);
}
