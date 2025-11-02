package service_desk_api.api.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParserBuilder;

@Component
public class JwtUtil {
	
	/*Deprecated. as of 0.10.0: use Keys.hmacShaKeyFor(bytes) to obtain the Key and then invoke signWith(Key) or signWith(Key, SignatureAlgorithm). This method will be removed in the 1.0 release.

Signs the constructed JWT using the specified algorithm with the specified key, producing a JWS.

Deprecation Notice: Deprecated as of 0.10.0

Use Keys.hmacShaKeyFor(bytes) to obtain the Key and then invoke signWith(Key) or signWith(Key, SignatureAlgorithm).

This method will be removed in the 1.0 release.*/
	
	//private static final String SECRET_KEY = "SuperChaveSecretaDosSonhosDeQualquerAPIRest";
	//private static final long EXPIRATION_TIME = 1000 * 60 * 60;
	
	@Value("${jwt.secretkey}")
	private String SECRET_KEY;
	
	@Value("${jwt.expiration}")
	private long EXPIRATION_TIME;
	
	private Key generateKey(String secretKey) {
		//byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		//return Keys.hmacShaKeyFor(keyBytes);
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(generateKey(SECRET_KEY), SignatureAlgorithm.HS256)
				//.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				//Parameters:
				//alg the JWS algorithm to use to digitally sign the JWT, thereby producing a JWS.
				//secretKey the algorithm-specific signing key to use to digitally sign the JWT.
				.compact();
	}
	
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	/*public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}*/
	
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
	
	public boolean isTokenValid(String token) {
		try {
			return !isTokenExpired(token);
		} catch (Exception e){
			return false;
		}
	}
	
	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
	
	private Claims extractAllClaims(String token) {
		System.out.println(token);
		/*return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();*/
		return Jwts.parserBuilder()
				.setSigningKey(generateKey(SECRET_KEY))
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

}
