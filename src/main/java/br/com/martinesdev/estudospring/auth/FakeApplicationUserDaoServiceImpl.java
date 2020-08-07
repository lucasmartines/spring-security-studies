package br.com.martinesdev.estudospring.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import br.com.martinesdev.estudospring.security.ApplicationUserRole;

@Repository("fake")
public class FakeApplicationUserDaoServiceImpl implements ApplicationUserDAO {

	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public FakeApplicationUserDaoServiceImpl(PasswordEncoder passwordEncoder) {

		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
		
		return getApplicationUsers()
				.stream()
				.filter( application -> username.equals( application.getUsername() ) )
				.findFirst();
		
	}
	
	private List<ApplicationUser> getApplicationUsers(){
		
		ApplicationUser maria = new ApplicationUser(
				ApplicationUserRole.STUDENT.getGrantedAuthorities(),
				passwordEncoder.encode("123456"),
				"maria" ,
				true, true,true,true
		);
		
		ApplicationUser lucas = new ApplicationUser(
				ApplicationUserRole.ADMIN.getGrantedAuthorities(),
				passwordEncoder.encode("123456"),
				"lucas" ,
				true, true,true,true
		);
		
		ApplicationUser tom = new ApplicationUser(
				ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
				passwordEncoder.encode("123456"),
				"tom" ,
				true, true,true,true
		);
		
		List<ApplicationUser> appUsers = Lists.newArrayList (
			maria , lucas , tom
		);
		
		return appUsers;
				
	}

}
