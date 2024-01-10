// package com.lvho.invoice.service;

// import io.jsonwebtoken.Claims; 
// import io.jsonwebtoken.Jwts; 
// import io.jsonwebtoken.SignatureAlgorithm; 
// import io.jsonwebtoken.io.Decoders; 
// import io.jsonwebtoken.security.Keys; 
// import org.springframework.security.core.userdetails.UserDetails; 
// import org.springframework.stereotype.Component; 

// import java.security.Key;
// import java.time.ZoneId;
// import java.time.ZonedDateTime;
// import java.util.Date; 
// import java.util.HashMap; 
// import java.util.Map;
// import java.util.function.Function; 

// @Component
// public class JwtService { 

// 	public static final String SECRET = "SaCBnRDZbFL+9zMSKDFjDg==rLithXVduoz3TlVzWRAa5Q==SaCBnRDZbFL+9zMSKDFjDg==rLithXVduoz3TlVzWRAa5Q==";
// 	public String generateToken(String userName) { 
// 		Map<String, Object> claims = new HashMap<>(); 
// 		return createToken(claims, userName); 
// 	} 

// 	private String createToken(Map<String, Object> claims, String userName) { 
// 		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
//         ZonedDateTime expirationTime = now.plusMinutes(60);
// 		return Jwts.builder() 
// 				.setClaims(claims) 
// 				.setSubject(userName) 
// 				.setIssuedAt(Date.from(now.toInstant())) 
// 				.setExpiration(Date.from(expirationTime.toInstant())) 
// 				.signWith(getSignKey(), SignatureAlgorithm.HS512).compact(); 
// 	} 

// 	private Key getSignKey() { 
// 		byte[] keyBytes= Decoders.BASE64.decode(SECRET); 
// 		return Keys.hmacShaKeyFor(keyBytes); 
// 	} 

// 	public String extractUsername(String token) { 
// 		return extractClaim(token, Claims::getSubject); 
// 	} 

// 	public Date extractExpiration(String token) { 
// 		return extractClaim(token, Claims::getExpiration);
// 	} 

// 	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
// 		final Claims claims = extractAllClaims(token); 
// 		return claimsResolver.apply(claims); 
// 	} 

// 	private Claims extractAllClaims(String token) { 
// 		return Jwts 
// 				.parserBuilder() 
// 				.setSigningKey(getSignKey()) 
// 				.build() 
// 				.parseClaimsJws(token) 
// 				.getBody(); 
// 	} 

// 	private Boolean isTokenExpired(String token) { 
// 		return extractExpiration(token).before(new Date()); 
// 	} 

// 	public Boolean validateToken(String token, UserDetails userDetails) { 
// 		final String username = extractUsername(token); 
// 		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
// 	}
// }