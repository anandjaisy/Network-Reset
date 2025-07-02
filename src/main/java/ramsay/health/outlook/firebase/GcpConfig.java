package ramsay.health.outlook.firebase;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("gcp")
public record GcpConfig(String project) {
}
