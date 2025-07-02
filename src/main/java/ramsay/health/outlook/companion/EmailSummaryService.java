package ramsay.health.outlook.companion;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.validation.validator.Validator;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import ramsay.health.IUserClaimsService;
import ramsay.health.outlook.UserProfile;
import ramsay.health.outlook.email.EmailSummaryRequest;
import ramsay.health.outlook.email.EmailSummaryResponse;
import ramsay.health.shared.MetaResponse;
import ramsay.health.shared.Response;
import ramsay.health.shared.Result;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Singleton
public record EmailSummaryService(ICompanionClient iCompanionClient,
                                  IUserClaimsService iUserClaimsService,
                                  Validator validator) implements IEmailSummaryService {
    private static final Logger LOG = Logger.getLogger(EmailSummaryService.class.getName());

    @Override
    public Result<Response<EmailSummaryResponse>> summerize(EmailSummaryRequest request, HttpRequest<?> httpRequest) {
        CompanionRequest companionRequest = CompanionRequest.toCompanionRequest(request, iUserClaimsService);

        // Validate the UserProfile
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(companionRequest.userProfile());
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            return errorResult(new Exception("UserProfile validation failed: " + errorMessage), null);
        }

        try {
            LOG.info("Companion http request started ...");
            HttpResponse<EmailSummaryResponse> response = iCompanionClient.summerize(companionRequest);

            if (response.getStatus() == HttpStatus.OK) {
                EmailSummaryResponse summary = enrichResponse(response.body(), request);
                LOG.info("Companion http request completed ...");
                return Result.of(new Response<>(summary, MetaResponse.Of(httpRequest)));
            }
            return errorResult(new Exception(String.format("Exception while calling Companion service : Http status : %s : and code : %d", response.getStatus(), response.code())), null);
        }
        catch (Exception ex) {
            return errorResult(ex, String.format("Exception while calling Companion service: %s", ex.getMessage()));
        }
    }

    private EmailSummaryResponse enrichResponse(EmailSummaryResponse response, EmailSummaryRequest request) {
        return response
                .withUserPrompt(request.userPrompt())
                .withBackground(normalize(response.background()))
                .withDraft_response(normalize(response.draft_response()));
    }

    private Result<Response<EmailSummaryResponse>> errorResult(Exception error, String errorMessage) {
        LOG.severe(errorMessage);
        LOG.severe(error.getMessage());
        return new Result<>(error);
    }

    private static String normalize(String value) {
        if (value == null)
            return null;

        String trimmed = value.trim();

        if (trimmed.isEmpty() || trimmed.equalsIgnoreCase("Not-Applicable") || trimmed.equalsIgnoreCase("None"))
            return null;

        return trimmed;
    }
}
