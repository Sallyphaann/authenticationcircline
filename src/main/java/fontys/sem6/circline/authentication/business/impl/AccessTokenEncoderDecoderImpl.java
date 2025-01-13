package fontys.sem6.circline.authentication.business.impl;

import fontys.sem6.circline.authentication.business.exception.InvalidAccessTokenException;
import fontys.sem6.circline.authentication.domain.AccessToken;
import fontys.sem6.circline.authentication.business.AccessTokenDecoder;
import fontys.sem6.circline.authentication.business.AccessTokenEncoder;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenDecoder, AccessTokenEncoder {
    private final Key key;
    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessTokenEncoded).getBody();

            String roles = claims.get("roles", String.class);

            return AccessToken.builder()
                    .subject(claims.getSubject())
                    .roles(roles)
                    .userId(claims.get("userId", Long.class))
                    .build();
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (accessToken.getRoles() != null) {
            claimsMap.put("roles", accessToken.getRoles());
        }
        if (accessToken.getUserId() != null) {
            claimsMap.put("id", accessToken.getUserId());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getSubject())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }
}
