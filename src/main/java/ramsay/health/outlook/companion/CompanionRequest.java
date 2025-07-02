package ramsay.health.outlook.companion;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.serde.annotation.Serdeable;
import ramsay.health.IUserClaimsService;
import ramsay.health.outlook.email.EmailSummaryRequest;
import ramsay.health.outlook.UserProfile;

import java.util.List;

@Serdeable
public record CompanionRequest(
        String sender,
        @JsonInclude(JsonInclude.Include.ALWAYS) List<String> to,
        @JsonInclude(JsonInclude.Include.ALWAYS) List<String> cc,
        String subject,
        String body,
        UserProfile userProfile
) {
    public static CompanionRequest toCompanionRequest(EmailSummaryRequest request, IUserClaimsService  userClaimsService) {
        return new CompanionRequest(
                request.from(),
                request.to(),
                request.cc(),
                request.title(),
                request.content(),
                new UserProfile(userClaimsService.getUserId(), userClaimsService.getName(), userClaimsService.getEmail(), userClaimsService.getFamilyName(), userClaimsService.getGivenName())
        );
    }
}
