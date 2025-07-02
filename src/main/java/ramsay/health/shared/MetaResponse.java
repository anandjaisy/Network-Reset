package ramsay.health.shared;

import io.micronaut.http.HttpRequest;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record MetaResponse(String trackingId, String apiName, String apiVersion, String method, String endpoint)
{
    public static MetaResponse Of(HttpRequest<?> request)
    {
        String method = request.getMethodName();
        String endpoint = request.getPath();
        String trackingId = request.getHeaders().get("X-Tracking-Id", String.class).orElse("unknown");

        return new MetaResponse(trackingId, ApplicationConstant.API_NAME, "v1", method, endpoint);
    }
}