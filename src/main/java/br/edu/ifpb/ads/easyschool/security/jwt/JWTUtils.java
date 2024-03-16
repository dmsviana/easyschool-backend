package br.edu.ifpb.ads.easyschool.security.jwt;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import br.edu.ifpb.ads.easyschool.model.Manager;
import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.model.User;
import br.edu.ifpb.ads.easyschool.repositories.ManagerRepository;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtils.class);

    private final StudentRepository studentRepository;
    private final ManagerRepository managerRepository;
    private final Environment environment;

    @Value("${api.app.jwtSecret}")
    private String jwtSecret;

    @Value("${api.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${api.app.jwtCookieName}")
    private String jwtCookie;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public ResponseCookie generateJwtCookie(UserDetails userPrincipal) {
        String username = userPrincipal.getUsername();
        Optional<Student> findedStudent = studentRepository.findByUsername(username);
        Optional<Manager> findedManager = managerRepository.findByUsername(username);

        if (findedStudent.isPresent()) {
            String jwt = generateTokenFromUsername(findedStudent.get());
            return buildJwtCookie(jwt);
        } else if (findedManager.isPresent()) {
            String jwt = generateTokenFromUsername(findedManager.get());
            return buildJwtCookie(jwt);
        }
        return null;
    }

    private ResponseCookie buildJwtCookie(String jwt) {
        final var builder = ResponseCookie.from(jwtCookie, jwt)
                .path("/").maxAge(3600000L)
                .secure(true)
                .httpOnly(true);

        String activeProfile = environment.getProperty("spring.profiles.active", "");
        if ("dev".equals(activeProfile)) {
            builder.domain("localhost");
        }

        return builder.build();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(User userEntity) {
        return Jwts.builder()
                .claim("id", userEntity.getId())
                .setSubject(userEntity.getUsername())
                .claim("name", userEntity.getName())
                .claim("email", userEntity.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public ResponseCookie getCleanJwtCookie() {
        final var builder = ResponseCookie.from(jwtCookie, "")
                .path("/")
                .maxAge(0)
                .secure(true)
                .httpOnly(true);

        if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
            builder.domain("localhost");
        }

        return builder.build();
    }

}
