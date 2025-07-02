package ramsay.health.outlook.email;

import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public record EmailSummaryRequest(String title, String content, String from, List<String> to, String userPrompt, List<String> cc) { }
