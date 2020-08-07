package br.com.martinesdev.estudospring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.martinesdev.estudospring.auth.ApplicationUserService;
import br.com.martinesdev.estudospring.jwt.JWTUserNamePassordAuthenticationFilter;
import br.com.martinesdev.estudospring.jwt.JWTverifierFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	
	
	@Autowired
	public SecurityConfig( 
					PasswordEncoder passwordEncoder, 
					ApplicationUserService applicationUserService
	) {
		
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
		
		System.out.println( "password: " + passwordEncoder.encode("123456"));
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception  {
		
		
		http 
			//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.csrf().disable()
				/* disable session because aws is stateless*/
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter( new JWTUserNamePassordAuthenticationFilter( authenticationManager() ) )
				.addFilterAfter( new JWTverifierFilter(), JWTUserNamePassordAuthenticationFilter.class )
			.authorizeRequests()
			
			.antMatchers("/","/home","/css/*","/js/*" )
				.permitAll()
				
			.antMatchers("/api/v1/**")
				.hasAnyRole( "STUDENT","ADMIN","ADMINTRAINEE" )
				
			.antMatchers( HttpMethod.DELETE , "/management/api/**")
				.hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
				
			.antMatchers( HttpMethod.POST   , "/management/api/**")
				.hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
				
			.antMatchers( HttpMethod.PUT    , "/management/api/**")
				.hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
				
			.antMatchers( HttpMethod.GET    , "/management/api/**")
				.hasAnyRole( ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINTRAINEE.name() )
			
			.anyRequest()
			.authenticated()
			
		;
		
//		.and()
//			.formLogin()
//			.loginPage("/login").permitAll().defaultSuccessUrl("/curses",true )
//			.passwordParameter("password")
//			.usernameParameter("username")
//		.and()
//			.rememberMe().tokenValiditySeconds( ( int )TimeUnit.DAYS.toSeconds( 21 ))
//			.rememberMeParameter("remember-me")
//				.key("IlikecookiesAndSecuritingThingsWithSpringBoot")
//				
//		.and()
//			.logout()
//				//.logoutRequestMatcher( new AntPathRequestMatcher("/logout","GET"))
//				.logoutUrl("/logout")
//				.clearAuthentication(true)
//				.invalidateHttpSession(true)
//				.deleteCookies("JSESSIONID","remember-me")
//				.logoutSuccessUrl("/login")
//		;
//	
			
			
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.authenticationProvider( daoAuthenticationProvider() );
	}
	
	@Bean
	 protected DaoAuthenticationProvider daoAuthenticationProvider(){
		 DaoAuthenticationProvider provider =  new DaoAuthenticationProvider(  );
		 
		 provider.setPasswordEncoder(passwordEncoder);
		 provider.setUserDetailsService(applicationUserService);
		 return provider;
	 }
	
	
//	 @Override
//	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	 	auth
//		 	.inMemoryAuthentication()
//			 	.withUser("user")
//			 	.password( passwordEncoder.encode("123456") )
//			 	.authorities("ROLE_USER","ROLE_ADMIN")
//	 		.and()
//			 	.withUser("admin")
//			 	.password( passwordEncoder.encode("123456") )
//			 	.authorities("ROLE_USER", "ROLE_ADMIN") 	;
//	 }

	 
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		
//		UserDetails ana = User.builder()
//			.username("ana")
//			.password( passwordEncoder.encode( "123456" ) )
//			.authorities( ApplicationUserRole.STUDENT.getGrantedAuthorities() )
//			//.roles( ApplicationUserRole.ADMIN.name() )
//			.build( );
//			
//		UserDetails other = User.builder()
//			.username("lucas")
//			.password( passwordEncoder.encode( "123456") )
//			.authorities( ApplicationUserRole.ADMIN.getGrantedAuthorities() )
//			//.roles( ApplicationUserRole.STUDENT.name() )
//			.build();
//		
//		UserDetails tom = User.builder()
//			.username("tom")
//			.password( passwordEncoder.encode( "123456") )
//			.authorities( ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities() )
//			//.roles( ApplicationUserRole.ADMINTRAINEE.name() )
//			.build();
//		
//		UserDetails maria = User.builder()
//				.username("maria")
//				.password( passwordEncoder.encode( "123456") )
//				.authorities( ApplicationUserRole.TEACHER.getGrantedAuthorities() )
//				.build();
//		
//		return new InMemoryUserDetailsManager( ana, tom , other , maria ); 
//	}
//	 
//	

}
