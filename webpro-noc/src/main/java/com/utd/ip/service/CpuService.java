package com.utd.ip.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utd.ips.rest.model.CPU_util;
import com.utd.ips.rest.model.SummaryDetailsFinal;
import com.utd.ips.rest.model.SummaryDetailsList;
import com.utd.ips.rest.model.SummaryFinal;
import com.utd.ips.rest.model.SummaryList;
import com.utd.ips.rest.model.SummaryVM;
import com.utd.ips.rest.model.TenantInfo;
import com.utd.ips.rest.model.TenantsData;


public class CpuService {
	
	public SummaryFinal getSummaryInfoList(CPU_util[] cpuDetails , TenantInfo  tenantInfo)
	{
		List<SummaryList> summaryList = new ArrayList<SummaryList>(); 
		
		for (int i = 0; i < tenantInfo.getTenants().size(); i++) {
			TenantsData objTenantData = tenantInfo.getTenants().get(i);
			SummaryList tmpSummaryList = new SummaryList();
			tmpSummaryList.setName(objTenantData.getName());
			tmpSummaryList.setTenant_id(objTenantData.getId());
			summaryList.add(tmpSummaryList);
		}
		
		for (int i = 0; i < summaryList.size(); i++) {
			summaryList.get(i).setCutil(-1);
		}
		
		String current_timestamp = cpuDetails[0].getTimestamp();
		for (int i = 0; i < cpuDetails.length; i++) {
			CPU_util cpuObj = cpuDetails[i];
			if (cpuObj.getTimestamp().equals(current_timestamp)) {
				for (int j = 0; j < summaryList.size(); j++) {
					if (cpuObj.getProject_id().equals(summaryList.get(j).getTenant_id())) {
						if (cpuObj.getCounter_volume() > summaryList.get(j).getCutil()) {
							summaryList.get(j).setInstance_name(cpuObj.getResource_metadata().getDisplay_name());
							summaryList.get(j).setCutil(cpuObj.getCounter_volume());
							summaryList.get(j).setTimestamp(cpuObj.getTimestamp());
							summaryList.get(j).setResource_id(cpuObj.getResource_id());
							break;
						} else { 
							break;
						}
					}
				}
			} else {
				break;
			}
		}
		SummaryFinal summaryFinal = new SummaryFinal();
		summaryFinal.setTenants(summaryList);
		return summaryFinal;
	}
	
	public SummaryDetailsFinal getProcessorInfoList(CPU_util[] cpuDetails , TenantInfo  tenantInfo)
	{
		List<SummaryDetailsList> summaryDetailsList = new ArrayList<SummaryDetailsList>();
		
		for (int i = 0; i < tenantInfo.getTenants().size(); i++) {
			TenantsData objTenantData = tenantInfo.getTenants().get(i);
			SummaryDetailsList tmpSummaryDetailsList = new SummaryDetailsList();
			tmpSummaryDetailsList.setName(objTenantData.getName());
			tmpSummaryDetailsList.setTenant_id(objTenantData.getId());
			summaryDetailsList.add(tmpSummaryDetailsList);
		}
		
		String current_timestamp = cpuDetails[0].getTimestamp();
		for (int i = 0; i < cpuDetails.length; i++) {
			CPU_util cpuObj = cpuDetails[i];
			if (cpuObj.getTimestamp().equals(current_timestamp)) {
				for (int j = 0; j < summaryDetailsList.size(); j++) {
					if (cpuObj.getProject_id().equals(summaryDetailsList.get(j).getTenant_id())
							&& (summaryDetailsList.get(j).getVms().size() == 0)) {
						SummaryVM tmpSummaryVM = new SummaryVM();
						tmpSummaryVM.setVmname(cpuObj.getResource_metadata().getDisplay_name());
						tmpSummaryVM.setCutil(cpuObj.getCounter_volume());
						tmpSummaryVM.setOwner(cpuObj.getUser_id());
						tmpSummaryVM.setUuid(cpuObj.getResource_id());
						summaryDetailsList.get(j).getVms().add(tmpSummaryVM);
						break;
					} else if (cpuObj.getProject_id().equals(summaryDetailsList.get(j).getTenant_id())
							&& (summaryDetailsList.get(j).getVms().size() > 0)) {
						for (int k = 0; k < summaryDetailsList.get(j).getVms().size(); k++) {
							if (summaryDetailsList.get(j).getVms().get(k).getVmname()
									.equals(cpuObj.getResource_metadata().getDisplay_name())) {
								summaryDetailsList.get(j).getVms().get(k)
										.setVmname(cpuObj.getResource_metadata().getDisplay_name());
								summaryDetailsList.get(j).getVms().get(k).setCutil(cpuObj.getCounter_volume());
								summaryDetailsList.get(j).getVms().get(k).setOwner(cpuObj.getUser_id());
								summaryDetailsList.get(j).getVms().get(k).setUuid(cpuObj.getResource_id());
								break;
							} else {
								SummaryVM tmpSummaryVM = new SummaryVM();
								tmpSummaryVM.setVmname(cpuObj.getResource_metadata().getDisplay_name());
								tmpSummaryVM.setCutil(cpuObj.getCounter_volume());
								tmpSummaryVM.setOwner(cpuObj.getUser_id());
								tmpSummaryVM.setUuid(cpuObj.getResource_id());
								summaryDetailsList.get(j).getVms().add(tmpSummaryVM);
								break;
							}
						}
					}
				}
			} else {
				break;
			}
		}
		SummaryDetailsFinal summaryDetailsFinal = new SummaryDetailsFinal();
		summaryDetailsFinal.setTenants(summaryDetailsList);
		return summaryDetailsFinal;
	}

}
