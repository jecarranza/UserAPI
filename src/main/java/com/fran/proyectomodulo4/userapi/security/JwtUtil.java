package com.fran.proyectomodulo4.userapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component //crea instancia de esta clase y tenla lista para inyectarla donde yo lo pida (@Autowired o por constructor)
public class JwtUtil {

    @Value("${jwt.secret}") //usamos la inyeccion del valor de application.properties a la variable SECRET_KEY
    private String SECRET_KEY;

    public String generateToken(UserDetails usuario){
        return Jwts.builder()// inicia la construccion del token
                .setSubject(usuario.getUsername()) // mete el usuario (el email), dentro del token
                .setIssuedAt(new Date()) //fecha de creacion (AHORA)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 + 60)) //tiempo de caducidad del token (1 hora)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256) // se realiza la firma con nuestra clave
                .compact(); // convierte todo un string codificado (ejemplo: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiBQcmluY2lwYWwiLCJpYXQiOjE3NjQyOTM1NjIsImV4cCI6MTc2NDI5MzYyM30
                            // .3b0jMfGC6dpiV9j1paGiJaeC6H8qZcUyNedN-NkIqBA)
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKeys())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder() //prepara el lector / decodificador
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // configura nuestra clave para verificar la misma firma
                .build() //completa la configuracion del decodificador
                .parseClaimsJws(token)// decodifica el token y verifica su firma utilizando la clave de la firma configurada y es fundamental usar
                                        // Jws para garantizar la verificacion de la misma
                .getBody()// extrae el (cuerpo del token) que contiene las declaraciones (como el nombre de usuario)
                .getSubject();// y obtiene el valor de la reclamacion que en este caso es el nombre de usuario que se guardo al crear el token (.setSubject(usuario.getUsername()))
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Key getKeys(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
