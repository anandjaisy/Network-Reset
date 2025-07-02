package ramsay.health.outlook.email;

import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public record EmailSummaryResponse(String request_id, String summary, List<String> categories, String draft_response,
                                   List<SuggestedAction> suggested_actions, String background, String userPrompt) {

    public EmailSummaryResponse withUserPrompt(String userPrompt) {
        return new EmailSummaryResponse(request_id, summary(), categories(), draft_response(), suggested_actions(), background(), userPrompt);
    }

    public EmailSummaryResponse withBackground(String background) {
        return new EmailSummaryResponse(request_id, summary(), categories(), draft_response(), suggested_actions(), background, userPrompt());
    }

    public EmailSummaryResponse withDraft_response(String draft_response) {
        return new EmailSummaryResponse(request_id, summary(), categories(), draft_response, suggested_actions(), background(), userPrompt);
    }
}
