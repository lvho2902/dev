package com.lvho.invoice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.lvho.invoice.entity.Role;
import com.lvho.invoice.service.UserInfoDetailsService;

@Component
public class JwtProvider { 

	private static final String SECRET_KEY_STRING = "SaCBnRDZbFL+9zMSKDFjDg==rLithXVduoz3TlVzWRAa5Q==SaCBnRDZbFL+9zMSKDFjDg==rLithXVduoz3TlVzWRAa5Q==";

    private static final long validityInMilliseconds = 3600000;

	@Lazy
    @Autowired
    private UserInfoDetailsService userInfoDetailsService;

    public String generateToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles.stream().map(role-> new SimpleGrantedAuthority(role.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(getSignKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userInfoDetailsService.loadUserByUsername(extractUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Key getSignKey() { 
		byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY_STRING); 
		return Keys.hmacShaKeyFor(keyBytes); 
	}

	public String extractUsername(String token) { 
		return extractClaim(token, Claims::getSubject); 
	}

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
		final Claims claims = extractAllClaims(token); 
		return claimsResolver.apply(claims); 
	}

    private Claims extractAllClaims(String token) { 
		return Jwts.parserBuilder() 
				.setSigningKey(getSignKey()) 
				.build() 
				.parseClaimsJws(token) 
				.getBody(); 
	}

    private Boolean isTokenExpired(String token) { 
		return extractExpiration(token).before(new Date()); 
	} 
    
    public Date extractExpiration(String token) { 
		return extractClaim(token, Claims::getExpiration);
	} 

	public Boolean validateToken(String token, UserDetails userDetails) { 
		final String username = extractUsername(token); 
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
	}

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


	// private String createToken(Map<String, Object> claims, String userName) { 
	// 	ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
    //     ZonedDateTime expirationTime = now.plusMinutes(60);
	// 	return Jwts.builder() 
	// 			.setClaims(claims) 
	// 			.setSubject(userName) 
	// 			.setIssuedAt(Date.from(now.toInstant())) 
	// 			.setExpiration(Date.from(expirationTime.toInstant())) 
	// 			.signWith(getSignKey(), SignatureAlgorithm.HS512).compact(); 
	// }
}