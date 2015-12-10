package com.utd.ip.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.utd.ip.service.RestService;
import com.utd.ips.rest.model.Access;
import com.utd.ips.rest.model.AccessWrapper;
import com.utd.ips.rest.model.CPU_util;
import com.utd.ips.rest.model.Credentials;
import com.utd.ips.rest.model.InstanceResponse;

@Controller
@RequestMapping("/mininoc")
public class HomeController {
	
	private final static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private RestService restService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHome(ModelMap model) {
		
		/*Credentials credentials = new Credentials();
		credentials.setTenantName(tenantName);
		credentials.setUsername(username);
		credentials.setPassword(password);
		
		ResponseEntity<AccessWrapper> responseAccess = restService.getAccessObject(credentials);
		
		AccessWrapper accessWrapper = responseAccess.getBody();
		Access access = accessWrapper.getAccess();
		//logger.debug("Toke : " +access + " token Id : " + access.getToken().getId() );
		if(access!=null && access.getToken()!=null)
		{
			logger.debug("Toke : " +access.getToken().getId());
			return "home";
		}	
		else
		{
			logger.debug("Toke : failed" );
			 return "redirect:/login/fail";
		}*/
		   
		/*if(userName.equals("parveen") && password.equals("pranjal"))
		{
			return "home";
		}
		else
		{
			return "redirect:/login/fail";
		}*/
		return "home";
		
	}
	
	@RequestMapping(value = "/processor/{uuid}", method = RequestMethod.GET)
	public String getProcessorPage(@PathVariable String  uuid,HttpServletRequest request, Model model) {
		
		// fake auth token 
		//Access access = new Access();
		//String xAuthToken = access.getToken().getId();
		// end data
		
		request.setAttribute("UUID", uuid);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Access access = (Access)auth.getPrincipal();
		Credentials credentials = (Credentials)auth.getCredentials();
		String xAuthToken = access.getToken().getId();
		
		InstanceResponse instanceResponse = new InstanceResponse();
		CPU_util cpuDetail = restService.getProcessorDetails(uuid, xAuthToken);
		
		
		instanceResponse.setCpuUtil(cpuDetail.getCounter_volume());
		instanceResponse.setUUID(cpuDetail.getResource_id());
		instanceResponse.setVmName(cpuDetail.getResource_metadata().getDisplay_name());
		
		restService.setTenantName(instanceResponse,access);
		
		
		/*instanceResponse.setCpuUtil(20);
		instanceResponse.setTenantName("ddfdsasdas");
		instanceResponse.setVmName("testVMMMMMM");
		instanceResponse.setUUID(uuid);
		*/
		model.addAttribute("instanceResponse", instanceResponse);
		
		
		
	   return "processor";
		
	}
	
	

}
