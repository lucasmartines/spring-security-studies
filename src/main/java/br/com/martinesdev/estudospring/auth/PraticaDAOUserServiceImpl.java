package br.com.martinesdev.estudospring.auth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository("real")
public class PraticaDAOUserServiceImpl implements ApplicationUserDAO {

	DataSource dt;
	private final String
		SQL_SELECT ="SELECT nome , senha , role from user where nome = ? ";
	
	
	@Autowired
	public PraticaDAOUserServiceImpl( DataSource dt ) {
		this.dt = dt;
	}
	
	@Override
	public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
		
		try {
			
			ResultSet rowOfUser = getResultSetOfUser(username);
			
			if( rowOfUser.next() ) 
			{				
				String name = rowOfUser.getString(1);
				String password = rowOfUser.getString(2);
				String authorityes = rowOfUser.getString(3) ;
				
				return Optional.of( getApplicationUser( name , password, authorityes ) );		
			}
			else {	return Optional.empty(); }
			
		} 
		catch (SQLException e) {  e.printStackTrace();	}
		
		return Optional.empty();
	}

	private ResultSet getResultSetOfUser( String username ) throws SQLException 
	{
		
		PreparedStatement stmt = dt.getConnection().prepareStatement( SQL_SELECT ) ; 
		stmt.setString(1, username);
		return stmt.executeQuery();	
	}

	private ApplicationUser getApplicationUser(
							String name,
							String password,
							String authorityes ) throws SQLException 
	{
		
		Set<SimpleGrantedAuthority> autority = getAllGrantedAuthorityFromDatabase(authorityes);	
		return new ApplicationUser
		(	autority , password, name,
			true,true,true,true
		);
	}
	
	Set<SimpleGrantedAuthority> getAllGrantedAuthorityFromDatabase( String role )
	{
		
		
		String [] roles = role.split(" ");
		
		
		Set<SimpleGrantedAuthority> authority = new HashSet<>();
		
		for( int x = 0; x < roles.length; x++ ) {
			authority.add ( new SimpleGrantedAuthority( roles[x] ) );
		}
		
		//authority.add ( new SimpleGrantedAuthority( role ) );
		return authority;
	}

}
