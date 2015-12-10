<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <style>
      body{
      	margin-top: 5%;
      	margin-bottom: 5%;
      	margin-left: 5%;
      	margin-right: 5%;
      }
      #tabsDiv {
    	min-height: 300px;
	  }
      .inlineP{
      	display: inline;
      }
      #sum {
      	padding: 25px;
      }
      #proc {
      	padding: 25px;
      }
      table {
      	padding: 15px;
    	width: 65%;
    	border: 1px solid #3366cc;
	  }
	  th {
    	height: 50px;
    	text-align: center;
    	vertical-align: middle;
    	background-color: #3366cc;
    	color: white;
    	border: 1px solid blue;
	  }
	  td {
	  	padding: 15px;
    	text-align: center;
    	vertical-align: middle;
    	border: 1px solid #3366cc;
	  }
    </style>
    <script>
      $(document).ready(function() {
      	var updateSummaryTab = function() {
			$("#sum").empty()
             $.ajax({
                 type: "GET",
                 url: "${pageContext.request.contextPath}/mininoc/ajax/summary",
                 dataType: "json",
                 success: function(jsonObj) {
					var counter = 0;
					var tenantTable = $("<table>", {id: "tt"+counter, class: "summaryClass table table-hover"});
					var tableHeading1 = $("<th>", {id: "th"+counter, class: "summaryClass"});
					$(tableHeading1).append("Tenant Name");
					var tableHeading2 = $("<th>", {id: "th"+counter, class: "summaryClass"});
					$(tableHeading2).append("Max CPU Utilization");
					var tableRow = $("<tr>", {id: "tr"+counter, class: "summaryClass"});
					$(tableRow).append(tableHeading1, tableHeading2);
					$(tenantTable).append(tableRow);
					$.each(jsonObj.tenants ,function() {
						counter = counter + 1;
						//for each tenant element create a new div
						var tenantDiv = $("<tr>", {id: "t"+counter, class: "summaryClass"});
						//create elements for each of the information that we need to display
						var tenantName = $("<td>", {id: "tname"+counter});
						$(tenantName).append(this.name);
						var tenantCpuUtil = $("<td>", {id: "tcpu"+counter});
						if(this.cutil == -1){
							statusImage = $("<img>", {id: "statusImage"+counter, class: "statusImgClass", src: "../resources/images/unavailable.jpeg", title: "Data Unavailable"});
						}else if(this.cutil >= 0 && this.cutil <75){
							statusImage = $("<img>", {id: "statusImage"+counter, class: "statusImgClass", src: "../resources/images/safe.jpeg", title: "Safe: "+this.cutil});
						}else if(this.cutil >= 75 && this.cutil <= 90){
							statusImage = $("<img>", {id: "statusImage"+counter, class: "statusImgClass", src: "../resources/images/warning.jpeg", title: "Warning: "+this.cutil});
						}else{
							statusImage = $("<img>", {id: "statusImage"+counter, class: "statusImgClass", src: "../resources/images/alarming.jpeg", title: "Alarming: "+this.cutil});
						}
						$(tenantCpuUtil).append(statusImage);
						//append all the values to the tenant div
						$(tenantDiv).append(tenantName, tenantCpuUtil);
						//append the tenant div to the summary;
						$(tenantTable).append(tenantDiv);
					});
					$("#sum").append(tenantTable);
                 },
                error: function(xhr) {
                  alert("An error occured: " + xhr.status + " " + xhr.statusText);
                }
             });
         } 
      	//requesting summary
      	$("#summarytab").click(updateSummaryTab);
		 
		 var updateProcessorsTab = function() {
			 $("#proc").empty();
             $.ajax({
                 type: "GET",
                 url: "${pageContext.request.contextPath}/mininoc/ajax/processor",
                 dataType: "json",
                 success: function(jsonObj) {
					var tenantCounter = 0;
					$.each(jsonObj.tenants ,function() {
						if(this.vms.length != 0){
							tenantCounter = tenantCounter + 1;
							//for each tenant element create a new div
							var tenantDiv = $("<div>", {id: "t_"+tenantCounter, class: "processorClass"});
							var tenantName = $("<h4>", {id: "tname_"+tenantCounter});
							$(tenantName).append(this.name);
							var tenantN = this.name;
							//vmDiv will host all the vm information for that tenant
							var vmDiv = $("<div>", {id: "vm_"+tenantCounter, class: "processorClass"});
							var vmCounter = 0;
							//creating table per VM for this tenant
							var vmTable = $("<table>", {id: "tt"+tenantCounter, class: "summaryClass table table-striped table-hover"});
							var tableHeading = $("<th>", {id: "th"+tenantCounter, class: "summaryClass"});
							$(tableHeading).append("VM UUID");
							var tableHeading1 = $("<th>", {id: "th"+tenantCounter, class: "summaryClass"});
							$(tableHeading1).append("VM Name");
							var tableHeading2 = $("<th>", {id: "th"+tenantCounter, class: "summaryClass"});
							$(tableHeading2).append("CPU Utilization");
							var tableRow = $("<tr>", {id: "tr"+tenantCounter, class: "summaryClass"});
							$(tableRow).append(tableHeading, tableHeading1, tableHeading2);
							$(vmTable).append(tableRow);
							//for each vm in this tenant create new elements
							$.each(this.vms, function(){
								//vmP ensures all the information per VM stays in one line
								var vmP = $("<tr>", {id: "vmp_"+tenantCounter+"_"+vmCounter});
								vmCounter = vmCounter + 1;
								//create elements for each of the information that we need to display
								var vmUuid = $("<td>", {id: "vmuuid_"+tenantCounter+"_"+vmCounter});
								var vmUuidLink = $("<a>", {href:"${pageContext.request.contextPath}/mininoc/processor/"+this.uuid+"?vmName="+this.vmname+"&cpuUtil="+this.cutil+"&tenantName="+tenantN});
								$(vmUuidLink).append(this.uuid);
								$(vmUuid).append(vmUuidLink);
								var vmName = $("<td>", {id: "vmname_"+tenantCounter+"_"+vmCounter});
								$(vmName).append(this.vmname);
								var vmCpuUtil = $("<td>", {id: "vmcpu_"+tenantCounter+"_"+vmCounter});
								if(this.cutil != -1){
									if(this.cutil == 0){
										$(vmCpuUtil).append("Shutdown");
									}else{
										$(vmCpuUtil).append(this.cutil);
									}
								}
								$(vmP).append(vmUuid, vmName, vmCpuUtil);
								$(vmTable).append(vmP);
								$(vmDiv).append(vmTable);
							})
							//append all the values to the tenant div
							$(tenantDiv).append(tenantName, vmDiv);
							//append the tenant div to the summary
							$("#proc").append(tenantDiv);
						}
					});
					$.each(jsonObj.tenants ,function() {
						if(this.vms.length == 0){
							tenantCounter = tenantCounter + 1;
							//for each tenant element create a new div
							var tenantDiv = $("<div>", {id: "t_"+tenantCounter, class: "processorClass"});
							var tenantName = $("<h4>", {id: "tname_"+tenantCounter});
							$(tenantName).append(this.name);
							var tenantN = this.name;
							//vmDiv will host all the vm information for that tenant
							var vmDiv = $("<div>", {id: "vm_"+tenantCounter, class: "processorClass"});
							var vmCounter = 0;
							//creating table per VM for this tenant
							var vmTable = $("<table>", {id: "tt"+tenantCounter, class: "summaryClass table table-striped table-hover"});
							var tableHeading = $("<th>", {id: "th"+tenantCounter, class: "summaryClass"});
							$(tableHeading).append("VM UUID");
							var tableHeading1 = $("<th>", {id: "th"+tenantCounter, class: "summaryClass"});
							$(tableHeading1).append("VM Name");
							var tableHeading2 = $("<th>", {id: "th"+tenantCounter, class: "summaryClass"});
							$(tableHeading2).append("CPU Utilization");
							var tableRow = $("<tr>", {id: "tr"+tenantCounter, class: "summaryClass"});
							$(tableRow).append(tableHeading, tableHeading1, tableHeading2);
							$(vmTable).append(tableRow);
							//for each vm in this tenant create new elements
							$.each(this.vms, function(){
								//vmP ensures all the information per VM stays in one line
								var vmP = $("<tr>", {id: "vmp_"+tenantCounter+"_"+vmCounter});
								vmCounter = vmCounter + 1;
								//create elements for each of the information that we need to display
								var vmUuid = $("<td>", {id: "vmuuid_"+tenantCounter+"_"+vmCounter});
								var vmUuidLink = $("<a>", {href:"${pageContext.request.contextPath}/mininoc/processor/"+this.uuid+"?vmName="+this.vmname+"&cpuUtil="+this.cutil+"&tenantName="+tenantN});
								$(vmUuidLink).append(this.uuid);
								$(vmUuid).append(vmUuidLink);
								var vmName = $("<td>", {id: "vmname_"+tenantCounter+"_"+vmCounter});
								$(vmName).append(this.vmname);
								var vmCpuUtil = $("<td>", {id: "vmcpu_"+tenantCounter+"_"+vmCounter});
								if(this.cutil != -1){
									if(this.cutil == 0){
										$(vmCpuUtil).append("Shutdown");
									}else{
										$(vmCpuUtil).append(this.cutil);
									}
								}
								$(vmP).append(vmUuid, vmName, vmCpuUtil);
								$(vmTable).append(vmP);
								$(vmDiv).append(vmTable);
							})
							//append all the values to the tenant div
							$(tenantDiv).append(tenantName, vmDiv);
							//append the tenant div to the summary
							$("#proc").append(tenantDiv);
						}
					});
                 }
             });
         }
         //requesting cpu utilization per tenant in detail for the processors tab
		 $("#processorstab").click(updateProcessorsTab); 
         
		 //call the updatesummarytab and updateprocessors tab functions repeatedly
		  var interval = 1000 * 100;
	      var setSummaryIntervalID = setInterval(updateSummaryTab, interval);
	      var setProcessorsIntervalID = setInterval(updateProcessorsTab, interval);
	      $("#setIntervalBtn").click(function(){
	        clearInterval(setSummaryIntervalID);
	        clearInterval(setProcessorsIntervalID);
	        var X = document.getElementById("timer").value;
	        if(X == "" || X == null){
	        	X = 100;
	        }
	        interval = 1000 * X;
	        setSummaryIntervalID = setInterval(updateSummaryTab, interval);
	        setProcessorsIntervalID = setInterval(updateProcessorsTab, interval);
	      });
      });
	  
    </script>
  </head>
  <body>
    <div class="tabs" id="tabsDiv">
      <ul class="nav nav-pills">
        <li class="active" id="summarytab"><a data-toggle="tab" href="#summary">Summary</a></li>
        <li id="processorstab"><a data-toggle="tab" href="#processors">Processors</a></li>
      </ul>
      <div class="tab-content">
        <div id="summary" class="tab-pane fade in active">
          <h3>Openstack Summary</h3>
          <center>
          <div id="sum"></div>
          </center>
        </div>
        <div id="processors" class="tab-pane fade">
          <h3>Processors Summary</h3>
          <center>
          <div id="proc"></div>
          </center>
        </div>
      </div>
    </div>
    <center>
    	Please Enter the timer value (Default 15 Seconds): 
      <input type="text" id="timer" min="15" max="300" placeholder="15-300 Seconds." required>
      </input>
      <input type="button" id="setIntervalBtn" value="Set Timer"></input>
    </center>
  </body>
</html>