package ramsay.health.outlook.email;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record SuggestedAction(String action, String owner, String due_date) {
}
