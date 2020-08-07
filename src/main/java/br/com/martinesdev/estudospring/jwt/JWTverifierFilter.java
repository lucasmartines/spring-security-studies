package br.com.martinesdev.estudospring.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTverifierFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal( 
								HttpServletRequest request, 
								HttpServletResponse response, 
								FilterChain filterChain)
								throws ServletException, IOException 
	{
		
		
		
		
		String authorizationHeader = request.getHeader("Authorization");
		
		
		
		
		if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer " )) {
			filterChain.doFilter(request, response);
			return;
		}
		
		
		
		try {
			String token = authorizationHeader.replace("Bearer " , "");
			
			Jws<Claims> claimsJws = Jwts.parser()
					.setSigningKey( Keys.hmacShaKeyFor( "ilikecookifflakssajfdkkalhsdyuyvcnbvg,mnbhes".getBytes() ) )
					.parseClaimsJws( token );
			
			Claims body = claimsJws.getBody();
			String username = body.getSubject();
			
			List<Map<String, String>> authorities = (List<Map<String,String>>) body.get("authorities");
			
			Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
					.map( m -> new SimpleGrantedAuthority(  m.get("authority") ))
					.collect(Collectors.toSet() );
			
			Authentication authentication =
				new UsernamePasswordAuthenticationToken
				(	username, null , grantedAuthorities );
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		catch( JwtException e) {
			throw new IllegalStateException("token is forjed");
		}
		
		filterChain.doFilter(request, response);
	}

}
