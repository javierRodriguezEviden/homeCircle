package com.app.homeCircle.Auth;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service // Marca esta clase como un servicio de Spring para que pueda ser inyectada en otras partes de la aplicación.
public class JwtService {

    // Clave secreta utilizada para firmar y verificar los tokens JWT.
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    /**
     * Genera un token JWT para un usuario dado.
     * 
     * @param email El email del usuario que será el subject del token.
     * @return El token JWT generado.
     */
    public String getToken(String email) {
        return Jwts.builder()
            .setSubject(email) // Establece el email como el subject del token.
            .setIssuedAt(new Date()) // Fecha de emisión del token.
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Fecha de expiración (10 horas).
            .signWith(getKey(), SignatureAlgorithm.HS256) // Firma el token con la clave secreta y el algoritmo HS256.
            .compact(); // Compacta y devuelve el token como una cadena.
    }

    /**
     * Extrae el email (subject) de un token JWT.
     * 
     * @param token El token JWT.
     * @return El email extraído del token.
     */
    public String getEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject); // Extrae el subject del token.
    }

    /**
     * Verifica si un token es válido para un usuario dado.
     * 
     * @param token El token JWT.
     * @param userDetails Los detalles del usuario.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String emailFromToken = getEmailFromToken(token); // Extrae el email del token.
        return (emailFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Verifica que el email coincida y que el token no haya expirado.
    }

    /**
     * Verifica si un token ha expirado.
     * 
     * @param token El token JWT.
     * @return true si el token ha expirado, false en caso contrario.
     */
    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date()); // Compara la fecha de expiración con la fecha actual.
    }

    /**
     * Obtiene la fecha de expiración de un token JWT.
     * 
     * @param token El token JWT.
     * @return La fecha de expiración del token.
     */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration); // Extrae la fecha de expiración del token.
    }

    /**
     * Extrae un claim específico del token JWT.
     * 
     * @param <T> El tipo del claim.
     * @param token El token JWT.
     * @param claimsResolver Una función para resolver el claim.
     * @return El valor del claim extraído.
     */
    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token); // Obtiene todos los claims del token.
        return claimsResolver.apply(claims); // Aplica la función para extraer el claim específico.
    }

    /**
     * Obtiene todos los claims de un token JWT.
     * 
     * @param token El token JWT.
     * @return Los claims del token.
     */
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getKey()) // Configura la clave secreta para verificar el token.
            .build()
            .parseClaimsJws(token) // Parsea el token y obtiene los claims.
            .getBody();
    }

    /**
     * Obtiene la clave secreta como un objeto `Key`.
     * 
     * @return La clave secreta.
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decodifica la clave secreta en formato Base64.
        return Keys.hmacShaKeyFor(keyBytes); // Crea una clave HMAC-SHA a partir de los bytes decodificados.
    }
}