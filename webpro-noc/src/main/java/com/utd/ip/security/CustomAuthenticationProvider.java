package com.utd.ip.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.utd.ip.controller.BaseController;
import com.utd.ip.service.RestService;
import com.utd.ips.rest.model.Access;
import com.utd.ips.rest.model.AccessWrapper;
import com.utd.ips.rest.model.Credentials;
import com.utd.ips.rest.model.Token;


public class CustomAuthenticationProvider implements AuthenticationProvider {

	
	private RestService restService;
	private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

	//private HttpServletRequest request;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	       String username = authentication.getName();
	        String password = authentication.getCredentials().toString();
	        String tenant = request.getParameter("tenantName");
	        
		Credentials credentials = new Credentials();
		credentials.setTenantName(tenant);
		credentials.setUsername(username);
		credentials.setPassword(password);
		
		/*Access access = new Access();
		access.setToken(new Token());*/
		Access access = null;
		try
		{
			logger.info("Inside authenticate");
			ResponseEntity<AccessWrapper> responseAccess = restService.getAccessObject(credentials);
			AccessWrapper accessWrapper = responseAccess.getBody();
			access = accessWrapper.getAccess();
			logger.info("token1234"+access.getToken().getId());
			
		}
		catch(Exception e)
		{
			logger.error("Try Catch Error", e);
			throw new UsernameNotFoundException("Exception occur during authentication" + e.getMessage());
		}
		finally{
			if(access==null)
			{
				throw new UsernameNotFoundException("User not found");
			}
			
		}
		
		credentials.setToken(access.getToken().getId());
		 List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		 grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		 
		/* if("parveen".equals(username) && "pranjal".equals(password) && "utd".equals(tenant) )
		 {
			 return new UsernamePasswordAuthenticationToken(access, credentials,grantedAuths);
		 }
		 else
		 {
			 throw new UsernameNotFoundException("User not found");
		 }
		 */
		 logger.info("REturning username"+access);
		 logger.info("REturning credentials"+credentials);
		 logger.info("REturning grantedAuths"+grantedAuths);
		 
		return new UsernamePasswordAuthenticationToken(access, credentials,grantedAuths); 
	}


	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
		//return true;
	}


	public RestService getRestService() {
		return restService;
	}


	public void setRestService(RestService restService) {
		this.restService = restService;
	}


	/*public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	*/
	

}
