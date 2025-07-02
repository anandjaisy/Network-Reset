package ramsay.health;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import org.reactivestreams.Publisher;

import java.text.ParseException;
import java.util.logging.Logger;

@Filter("/**")  // Apply to all routes
public class JwtClaimsFilter implements HttpServerFilter {

    private final UserClaimsService userClaimsService;
    private static final Logger LOG = Logger.getLogger(JwtClaimsFilter.class.getName());

    public JwtClaimsFilter(UserClaimsService userClaimsService) {
        this.userClaimsService = userClaimsService;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request,
                                                      ServerFilterChain chain) {

        String authHeader = request.getHeaders().getAuthorization().orElse(null);

        if (authHeader != null && authHeader.toLowerCase().startsWith("bearer ")) {
            String token = authHeader.substring(7).trim(); // Remove "Bearer " prefix

            try {
                JWT jwt = JWTParser.parse(token);
                JWTClaimsSet claims = jwt.getJWTClaimsSet();

                // Extract claims and populate the service
                userClaimsService.setUserId(claims.getStringClaim("oid"));
                userClaimsService.setEmail(claims.getStringClaim("unique_name"));
                userClaimsService.setFamilyName(claims.getStringClaim("family_name"));
                userClaimsService.setGivenName(claims.getStringClaim("given_name"));
                userClaimsService.setName(claims.getStringClaim("name"));
                LOG.info("User claims set with service");
            } catch (ParseException e) {
                LOG.severe("Error parsing JWT Token");
                LOG.severe(e.getMessage());
                return Publishers.just(HttpResponse.badRequest("Invalid JWT token"));
            }
        } else {
            LOG.severe("Authorization Header is missing or invalid");
            return Publishers.just(HttpResponse.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid Authorization header"));
        }
        return chain.proceed(request);
    }
}