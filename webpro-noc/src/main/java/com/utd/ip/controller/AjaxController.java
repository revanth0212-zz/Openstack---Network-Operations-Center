package com.utd.ip.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utd.ip.service.CpuService;
import com.utd.ip.service.RestService;
import com.utd.ips.rest.model.Access;
import com.utd.ips.rest.model.AccessWrapper;
import com.utd.ips.rest.model.CPU_util;
import com.utd.ips.rest.model.Credentials;
import com.utd.ips.rest.model.InstanceResponse;
import com.utd.ips.rest.model.SummaryDetailsFinal;
import com.utd.ips.rest.model.SummaryFinal;
import com.utd.ips.rest.model.TenantInfo;


@Controller
@RequestMapping("/mininoc/ajax")
public class AjaxController {
	
	private final static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private RestService restService;
	
	@Autowired
	private CpuService cpuService;
	
	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	public ResponseEntity<SummaryFinal> getSummaryInfo() {
		
		// session security implementation
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Access access = (Access)auth.getPrincipal();
		Credentials credentials = (Credentials)auth.getCredentials();
		logger.info("check summary info" + credentials + "   "+ access);
		/* start with fake data*/
		
	/*	Credentials credentials = new Credentials();
		credentials.setTenantName("UTD");
		credentials.setUsername("pranjal-admin");
		credentials.setPassword("pranjal");*/
		

		/*ResponseEntity<AccessWrapper> responseAccess = restService.getAccessObject(credentials);
		AccessWrapper accessWrapper = responseAccess.getBody();
		Access access = accessWrapper.getAccess();
		if(access==null && access.getToken()==null)
			return new ResponseEntity<SummaryFinal>(HttpStatus.UNAUTHORIZED);
		credentials.setToken(access.getToken().getId());*/
		// end fake data
		ResponseEntity<TenantInfo> responseTenantInfo = restService.getTenantInfo(access);
		TenantInfo  tenantInfo= responseTenantInfo.getBody();
		
		
		ResponseEntity<CPU_util[]> responseCPU = restService.getCPUUtilList(credentials, 30);
		CPU_util[]  cpuUtilList= responseCPU.getBody();
		
		SummaryFinal summaryFinal = cpuService.getSummaryInfoList(cpuUtilList, tenantInfo);
		
		//SummaryFinal summaryFinal  = new SummaryFinal();
		
		return new ResponseEntity<SummaryFinal>(summaryFinal, HttpStatus.OK);	
		
	}
	
	@RequestMapping(value = "/processor", method = RequestMethod.GET)
	public ResponseEntity<SummaryDetailsFinal> getInstanceInfo() {
		
		// session security implementation
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Access access = (Access)auth.getPrincipal();
		Credentials credentials = (Credentials)auth.getCredentials();
		logger.info("check processor info" + credentials + "   "+ access);
		/* start with fake data
		Credentials credentials = new Credentials();
		credentials.setTenantName("UTD");
		credentials.setUsername("pranjal-admin");
		credentials.setPassword("pranjal");
		
		ResponseEntity<AccessWrapper> responseAccess = restService.getAccessObject(credentials);
		AccessWrapper accessWrapper = responseAccess.getBody();
		Access access = accessWrapper.getAccess();
		if(access==null && access.getToken()==null)
			return new ResponseEntity<SummaryDetailsFinal>(HttpStatus.UNAUTHORIZED);
		credentials.setToken(access.getToken().getId());*/
		// end fake data*/
		
		ResponseEntity<TenantInfo> responseTenantInfo = restService.getTenantInfo(access);
		TenantInfo  tenantInfo= responseTenantInfo.getBody();
		
		
		ResponseEntity<CPU_util[]> responseCPU = restService.getCPUUtilList(credentials, 30);
		CPU_util[]  cpuUtilList= responseCPU.getBody();
		
		SummaryDetailsFinal summaryDetailsFinal = cpuService.getProcessorInfoList(cpuUtilList, tenantInfo);
		
		//SummaryDetailsFinal summaryDetailsFinal = new SummaryDetailsFinal();
		
		return new ResponseEntity<SummaryDetailsFinal>(summaryDetailsFinal, HttpStatus.OK);	
		
	}
	
	@RequestMapping(value = "/instance/{UUID}", method = RequestMethod.GET)
	public ResponseEntity<InstanceResponse> getProcessorPage(@PathVariable String UUID) {
		
		// fake auth token 
		//Access access = new Access();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Access access = (Access)auth.getPrincipal();
		Credentials credentials = (Credentials)auth.getCredentials();
		logger.info("check instance info" + credentials + "   "+ access);
		String xAuthToken = access.getToken().getId();
		// end data
		
		InstanceResponse instanceResponse = new InstanceResponse();
		CPU_util cpuDetail = restService.getProcessorDetails(UUID, xAuthToken);
		
		
		instanceResponse.setCpuUtil(cpuDetail.getCounter_volume());
		instanceResponse.setUUID(cpuDetail.getResource_id());
		instanceResponse.setVmName(cpuDetail.getResource_metadata().getDisplay_name());
		
		
		/*instanceResponse.setCpuUtil(60);
		instanceResponse.setTenantName("TestTenant");
		instanceResponse.setVmName("testVM");
		instanceResponse.setUUID(UUID);*/
		
		//model.addAttribute("instanceResponse", instanceResponse);
		
		
		
		return new ResponseEntity<InstanceResponse>(instanceResponse, HttpStatus.OK);	
		
	}

}
