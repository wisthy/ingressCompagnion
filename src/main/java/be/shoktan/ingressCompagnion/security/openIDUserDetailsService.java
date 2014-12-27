package be.shoktan.ingressCompagnion.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import be.shoktan.ingressCompagnion.bean.RegisteredAgent;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;
import be.shoktan.ingressCompagnion.repository.RegisteredAgentRepository;

public class openIDUserDetailsService implements
		AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
	
	static final Logger logger = LoggerFactory.getLogger(openIDUserDetailsService.class);
	private RegisteredAgentRepository repo;

	

	/**
	 * @param repo
	 */
	public openIDUserDetailsService(RegisteredAgentRepository repo) {
		super();
		this.repo = repo;
	}



	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
		
		String email = null;
		
		for(OpenIDAttribute attr : token.getAttributes()){
			if(StringUtils.equals("email", attr.getName())){
				List<String> values = attr.getValues();
				if(values != null && values.size() >= 1){
					email = values.get(0);
					break;
				}
			}
		}
		
		if(email != null){
			if(logger.isDebugEnabled())logger.debug("looking for user with email '"+email+"'");
			try {
				RegisteredAgent agent = repo.findByEmail(email);
				List<GrantedAuthority> auth = new ArrayList<>();
				auth.add(new SimpleGrantedAuthority("ROLE_USER"));
				return new User(agent.getCodename(), agent.getCodename(), auth);
			} catch (NotFoundException e) {
				String message = "user with email '"+email+"' not found in the database";
				logger.error(message, e);
				throw new UsernameNotFoundException(message, e);
			}
		}else{
			String message = "no email received from openid";
			logger.error(message);
			throw new UsernameNotFoundException(message);
		}
	}

}
