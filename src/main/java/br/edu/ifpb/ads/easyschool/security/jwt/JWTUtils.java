package br.edu.ifpb.ads.easyschool.security.jwt;


import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import br.edu.ifpb.ads.easyschool.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtils.class);

    private final StudentRepository studentRepository;
    private final Environment environment;

    @Value("${api.app.jwtSecret}")
    private String jwtSecret;

    @Value("${api.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${api.app.jwtCookieName}")
    private String jwtCookie;


    public String getJwtFromCookies(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if(cookie != null){
            return cookie.getValue();
        }
        return null;
    }


    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        final Optional<Student> findedStudent = studentRepository.findByUsername(userPrincipal.getUsername());

        if (findedStudent.isPresent()) {
            String jwt = generateTokenFromUsername(findedStudent.get());
            final var builder = ResponseCookie.from(jwtCookie, jwt)
                    .path("/").maxAge(3600000L)
                    .secure(true)
                    .httpOnly(true);

            if (environment.getProperty("spring.profiles.active").equals("dev")) {
                builder.domain("localhost");
            }

            return builder.build();
        }

        return null;
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

    public String generateTokenFromUsername(Student studentEntity) {
        return Jwts.builder()
                .claim("id", studentEntity.getId())
                .setSubject(studentEntity.getUsername())
                .claim("name", studentEntity.getName())
                .claim("email", studentEntity.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


}
