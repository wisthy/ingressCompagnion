package be.shoktan.ingressCompagnion.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import be.shoktan.ingressCompagnion.bean.RegisteredAgent;
import be.shoktan.ingressCompagnion.exceptions.NotFoundException;
import be.shoktan.ingressCompagnion.repository.RegisteredAgentRepository;

public class IngressAgentService implements UserDetailsService{
	static final Logger logger = LoggerFactory.getLogger(IngressAgentService.class);
	private final RegisteredAgentRepository repo;

	
	/**
	 * @param repo
	 */
	public IngressAgentService(RegisteredAgentRepository repo) {
		super();
		this.repo = repo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		RegisteredAgent agent;
		try {
			agent = repo.findByCodename(username);
		} catch (NotFoundException e) {
			String message = "User '"+username+"' not found";
			logger.error(message, e);
			throw new UsernameNotFoundException(message, e);
			
		}
		if(agent != null){
			List<GrantedAuthority> auth = new ArrayList<>();
			auth.add(new SimpleGrantedAuthority("ROLE_USER"));
			return new User(agent.getCodename(), agent.getCodename(), auth);
		}else{
			String message = "User '"+username+"' not found";
			logger.error(message);
			throw new UsernameNotFoundException(message);
		}
	}
}
