package com.utd.ip.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.utd.ips.rest.model.Access;
import com.utd.ips.rest.model.AccessWrapper;
import com.utd.ips.rest.model.CPU_util;
import com.utd.ips.rest.model.Credentials;
import com.utd.ips.rest.model.HeaderAuth;
import com.utd.ips.rest.model.InstanceJson;
import com.utd.ips.rest.model.InstanceQuery;
import com.utd.ips.rest.model.InstanceResponse;
import com.utd.ips.rest.model.PasswordCred;
import com.utd.ips.rest.model.Tenant;
import com.utd.ips.rest.model.TenantInfo;

public class RestService {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	private final static Logger logger = LoggerFactory.getLogger(RestService.class);
	
	public void testService()
	{
		logger.debug("Service called");
	}
	
	public ResponseEntity<AccessWrapper> getAccessObject(Credentials credentials)
	{
		logger.debug("Password : "+ credentials.getPassword()  + " : "+ credentials.getUsername());
		HeaderAuth headerAuth = new HeaderAuth();
		Tenant auth = new Tenant();
		PasswordCred pass = new PasswordCred();
		pass.setPassword(credentials.getPassword());
		pass.setUsername(credentials.getUsername());
		auth.setPasswordCredentials(pass);
		auth.setTenantName(credentials.getTenantName());
		headerAuth.setAuth(auth);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<HeaderAuth> request = new HttpEntity<HeaderAuth>(headerAuth, headers);
		ResponseEntity<AccessWrapper> responseEntity = restTemplate.postForEntity("http://10.0.0.10:5000/v2.0/tokens", request,AccessWrapper.class);
		logger.debug("Test Status : "+ responseEntity.getStatusCode()  + " : "+ responseEntity.toString() + " : " + responseEntity.getBody());
		//ResponseEntity<Access> responseEntity = restTemplate.postForEntity("http://localhost:9002/server/service/token/local/test", request,Access.class);
		
		return responseEntity;
	}
	
	public ResponseEntity<TenantInfo> getTenantInfo(Access access)
	{
		String urlTenantInfo = "http://10.0.0.10:35357/v2.0/tenants";
		// request = new HttpEntity<HttpHeaders>(headers);
		// headers.set("X-Auth-Token", access.getToken().getId());
		HttpHeaders headers2 = new HttpHeaders();
		// headers2.setContentType(MediaType.APPLICATION_JSON);
		headers2.add("Accept", "application/json");
		// headers2.put
		headers2.add("X-Auth-Token", access.getToken().getId());
		// headers2.add("X-Auth-Token", access.getToken().getId());
		HttpEntity<?> request2 = new HttpEntity<Object>(headers2);

		ResponseEntity<TenantInfo> responseEntity2 = restTemplate.exchange(urlTenantInfo, HttpMethod.GET, request2,
				TenantInfo.class);
		return responseEntity2;
	}
	
	public ResponseEntity<CPU_util[]> getCPUUtilList(Credentials credentials, Integer limit)
	{
		String urlCpuUtil = null;
		if(limit==null)
		{
			urlCpuUtil = "http://10.0.0.10:8777/v2/meters/cpu_util";
		}
		else
		{
			urlCpuUtil = "http://10.0.0.10:8777/v2/meters/cpu_util?limit="+limit;
		}
		HttpHeaders headers3 = new HttpHeaders();
		headers3.add("Accept", "application/json");
		logger.info("check cred"+credentials);
		headers3.add("X-Auth_Token", credentials.getToken());
		HttpEntity<?> request3 = new HttpEntity<Object>(headers3);
		ResponseEntity<CPU_util[]> responseEntity3 = restTemplate.exchange(urlCpuUtil, HttpMethod.GET, request3,
				CPU_util[].class);
		return responseEntity3;
		
	}
	
	public CPU_util  getProcessorDetails(String uuid, String xAuthToken)
	{
		InstanceJson instance = new InstanceJson();
		InstanceQuery query = new InstanceQuery();
		query.setField("resource_id");
		query.setOp("eq");
		query.setValue(uuid);
		instance.setQ(query);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("X-Auth_Token",xAuthToken);
		HttpEntity<InstanceJson> request = new HttpEntity<InstanceJson>(instance, headers);
		logger.info("curl request"+ request);
		ResponseEntity<CPU_util[]> responseEntity3 = restTemplate.exchange("http://10.0.0.10:8777/v2/meters/cpu_util?limit=1",  HttpMethod.GET, request,
				CPU_util[].class);
		CPU_util[] cpuUtilList = responseEntity3.getBody();
		if(cpuUtilList==null && cpuUtilList.length==0)
			return null;
		
		return cpuUtilList[0];
		
	}
	
	public void setTenantName(InstanceResponse instanceResponse, Access access)
	{
		ResponseEntity<TenantInfo> responseEntity2 = getTenantInfo(access);
		TenantInfo  tenantInfo = responseEntity2.getBody();
		for (int j = 0; j < tenantInfo.getTenants().size(); j++) {
			if (tenantInfo.getTenants().get(j).getId().equals(instanceResponse.getUUID())) {
				instanceResponse.setTenantName(tenantInfo.getTenants().get(j).getName());
				break;
			}
		}
	}
	
	

	
}
