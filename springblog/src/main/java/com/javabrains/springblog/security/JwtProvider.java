package com.javabrains.springblog.security;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.*;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;
import io.jsonwebtoken.Claims;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.javabrains.springblog.exception.SpringBlogException;

import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;

@Service
public class JwtProvider {
	
	
	private KeyStore keyStore;
	
	@PostConstruct
	public void init() throws java.io.IOException {
		try {
		keyStore = KeyStore.getInstance("JKS");
		InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
		keyStore.load(resourceAsStream,"sumitpaw".toCharArray());
		}catch(KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			 throw new SpringBlogException("Exception occured while loading keystore");
		}

	}
	
	public boolean validateToken(String jwt) {
	        Jwts.parser().setSigningKey(getPrivateKey()).parseClaimsJws(jwt);
	        return true;
	}
	
	 public String generateToken(Authentication authentication) {
	        User principal = (User) authentication.getPrincipal();
	        return Jwts.builder()
	                .setSubject(principal.getUsername())
	                .signWith(getPrivateKey())
	                .compact();
	 }

	 private PrivateKey getPrivateKey(){
	        try {
	            return (PrivateKey) keyStore.getKey("springblog", "sumitpaw".toCharArray());
	        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
	            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
	        }
	 } 
	 

	public String getUsernameFromJWT(String token) {
	        Claims claims = Jwts.parser()
	                .setSigningKey(getPublickey())
	                .parseClaimsJws(token)
	                .getBody();

	        return claims.getSubject();
	 }

	private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringBlogException("Exception occured while retrieving public key from keystore");
        }
    }

}
