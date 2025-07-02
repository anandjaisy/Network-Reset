package ramsay.health.outlook;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;

@Serdeable
public record UserProfile(@JsonInclude(JsonInclude.Include.ALWAYS) String id,

                          @JsonInclude(JsonInclude.Include.ALWAYS) String name,

                          @NotNull @JsonInclude(JsonInclude.Include.ALWAYS)
                          String email,

                          @JsonInclude(JsonInclude.Include.ALWAYS) String familyName,
                          @JsonInclude(JsonInclude.Include.ALWAYS) String givenName) {
}
