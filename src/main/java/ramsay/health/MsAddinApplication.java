package ramsay.health;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
        info = @Info(
                title = "ms-addin",
                version = "0.0"
        )
)
public class MsAddinApplication {

    public static void main(String[] args) {
        Micronaut.run(MsAddinApplication.class, args);
    }
}