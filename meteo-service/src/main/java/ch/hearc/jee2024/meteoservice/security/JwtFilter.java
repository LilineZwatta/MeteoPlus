package ch.hearc.jee2024.meteoservice.security;

import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String authHeader = req.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                SignedJWT jwt = SignedJWT.parse(token);

                // Test : afficher le login (subject)
                String subject = jwt.getJWTClaimsSet().getSubject();
                System.out.println("Utilisateur authentifié : " + subject);

                // Tu pourrais vérifier l'expiration ici aussi (facultatif)

                chain.doFilter(request, response);
                return;

            } catch (ParseException e) {
                System.err.println("Token JWT invalide : " + e.getMessage());
            }
        }

        // Aucun token ou token invalide
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
