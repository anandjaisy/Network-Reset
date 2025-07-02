package ramsay.health.outlook.feedback;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Serdeable
public record UserFeedbackRequest(@NotNull String requestId,
                                  @NotNull String itemId,
                                  Boolean thumbUp,
                                  Boolean thumbDown,
                                  @Size(min = 10)  String feedback,
                                  FeedbackType feedbackType) {
}
