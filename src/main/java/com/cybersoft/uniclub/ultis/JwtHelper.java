package com.cybersoft.uniclub.ultis;

import com.cybersoft.uniclub.exception.AuthenException;
import com.cybersoft.uniclub.request.IntrospectRequest;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;
    public String generateToken(String data) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(data)
                .issuer("uniclub.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            System.out.println("Cannot create token");
            throw new RuntimeException(e);

        }
    }


    public String generateToken2(String data) {
//         tao key ngau nhien
//         SecretKey secretKey = Jwts.SIG.HS512.key().build();
//         Bien object key thanh String de luu tru lai
//        String key = Encoders.BASE64.encode(secretKey.getEncoded());
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SIGNER_KEY));
        String token2 = Jwts.builder().subject(data).signWith(secretKey).compact();
        String token = Jwts.builder()
                .header()
                .and()
                .issuer("uniclub.com")
                .subject(data)
                .issuedAt(new Date())
                .expiration(new Date(Instant.now()
                        .plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .signWith(secretKey)
                .compact();
        return token;
    }


    public boolean introspectToken(IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean verified = signedJWT.verify(verifier);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return verified && expiryTime.after(new Date());
    }
    public String introspectToken2(IntrospectRequest request){
        String token = request.getToken();
        // tao 1 secret key tu string key
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SIGNER_KEY));
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new AuthenException("Invalid or expired JWT token");
        }

    }
}
