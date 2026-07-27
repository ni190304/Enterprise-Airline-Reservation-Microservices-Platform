// package com.project.user_service.config;

// import java.util.Collection;
// import java.util.Date;

// import javax.crypto.SecretKey;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;

// public class JwtProvider {

//     private final SecretKey key = Keys.hmacShaKeyFor(
//         JwtConstant.SECRET_KEY.getBytes()
//     );

//     // public String generateToken(Authentication auth, Long userId){
//     //     Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//     //     String roles = populateAuthorities(authorities);
//     //     // String jwt = Jwts.builder()
//     //     // .issuedAt(new Date())
//     //     // .expiration(new Date(System.currentTimeMillis()+86400000))
//     //     // .claim("email", auth.getName())
//     //     // .claim("authorities", roles)
//     //     // .claim("userid", userId)
        
//     // }

//     // private String populateAuthorities(Collection<? extends GrantedAuthority> authorities){

//     // }
// }
