package challenge.castgroup.bank.accounts.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import challenge.castgroup.bank.accounts.util.RoleEnum;

/***
 * 
 * @author robson.a.mello
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationFilter authenticationFilter;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.httpFirewall(defaultHttpFirewall());
	}
	
	@Bean
	public HttpFirewall defaultHttpFirewall() {
	    return new DefaultHttpFirewall();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().configurationSource(corsConfigurationSource());

		http.csrf().disable();

		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/signin").permitAll()
			
			.antMatchers(HttpMethod.GET, "/person/**")
				.hasAnyRole(RoleEnum.ADMIN.getDescription(), RoleEnum.MANAGER.getDescription(), RoleEnum.GUEST.getDescription())
			.antMatchers(HttpMethod.POST, "/person/**")			
				.hasAnyRole(RoleEnum.ADMIN.getDescription(), RoleEnum.MANAGER.getDescription())
			.antMatchers(HttpMethod.PUT, "/person/**")			
				.hasAnyRole(RoleEnum.ADMIN.getDescription(), RoleEnum.MANAGER.getDescription())
			.antMatchers(HttpMethod.DELETE, "/person/**")			
				.hasAnyRole(RoleEnum.ADMIN.getDescription(), RoleEnum.MANAGER.getDescription())
			.antMatchers(HttpMethod.POST, "/finantialtransaction/**")
				.hasAnyRole(RoleEnum.ADMIN.getDescription(), RoleEnum.MANAGER.getDescription())
				
			.antMatchers(HttpMethod.POST, "/users/**")
				.hasRole(RoleEnum.ADMIN.getDescription())
				
		.anyRequest().authenticated();
		
		http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());

		/*
		 * All Requests will go through this filter.
		 */
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
