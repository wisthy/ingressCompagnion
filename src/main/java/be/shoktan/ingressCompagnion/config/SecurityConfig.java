/**
 * 
 */
package be.shoktan.ingressCompagnion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import be.shoktan.ingressCompagnion.repository.RegisteredAgentRepository;
import be.shoktan.ingressCompagnion.security.IngressAgentService;
import be.shoktan.ingressCompagnion.security.openIDUserDetailsService;

/**
 * @author wisthler
 *
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	RegisteredAgentRepository repo;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
//	 */
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(new IngressAgentService(repo));
//	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/static/*").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.openidLogin()
				.loginPage("/sec/login")
				.permitAll()
				.authenticationUserDetailsService(new openIDUserDetailsService(repo))
				.attributeExchange("https://www.google.com/.*")
					.attribute("email")
						.type("http://axschema.org/contact/email")
						.required(true)
						.and()
					.attribute("firstname")
						.type("http://axschema.org/namePerson/first")
						.required(true)
						.and()
					.attribute("lastname")
						.type("http://axschema.org/namePerson/last")
						.required(true);

	}

}
