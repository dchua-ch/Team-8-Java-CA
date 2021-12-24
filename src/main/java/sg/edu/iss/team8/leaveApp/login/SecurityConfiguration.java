package sg.edu.iss.team8.leaveApp.login;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	@Autowired
	private DataSource dataSource;
	
   @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from user where username=?")
            .authoritiesByUsernameQuery("select username, user_type from user where username=?")
        ;
    }
 
    
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/adminDashboard").hasAuthority("admin")
				.antMatchers("/dashboard").hasAnyAuthority("employee", "manager")
				.anyRequest().authenticated()
			.and().formLogin()
				.successHandler(successHandler)
			.permitAll()
			.and()
			.sessionManagement()
			.and()
			.logout();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
}

    
	