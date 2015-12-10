<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="java.text.SimpleDateFormat" %>
	<%@ page import="java.util.Date" %>
	<%@ page import= "java.util.TimeZone" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>
      Processors
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> 
    <script>
      $(document).ready(function(){
        var UUID;
        var y;
        var m;
        var d;
        var h;
        var min;
        var s;
        var dateT;
        $(function (){
          UUID = document.getElementById("uuid").innerHTML;
          y = document.getElementById("year").value;
          m = document.getElementById("month").value;
          d = document.getElementById("day").value;
          h = document.getElementById("hour").value;
          min = document.getElementById("minute").value;
          s = document.getElementById("second").value;
          dateT = document.getElementById("normalDate").value;
        });
        // Build the chart
      	$(function () {
        $('#pie').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie',
      renterTo: 'pie'
            },
            title: {
                text: 'VM Processor Usage Statistics'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: "Usage",
                colorByPoint: true,
                data: [{
                    name: "Used",
                    y: 0,
                }, {
                    name: "Free",
                    y: 100,
                    sliced: true,
                    selected: true
                }]
            }]
        });
      });
      $(function () {
      $('#graph').highcharts({
        chart: {
            type: 'spline',
      renderTo: 'graph'
        },
        title: {
            text: 'Processor Usage'
        },
        subtitle: {
            text: 'VM Processor Usage'
        },
        xAxis: {
            type: 'datetime',
            labels: {
                overflow: 'justify'
            }
        },
        yAxis: {
            title: {
                text: 'Percentage'
            },
            minorGridLineWidth: 0,
            gridLineWidth: 0,
            alternateGridColor: null,
            plotBands: [{ 
                from: 0,
                to: 60,
                color: 'rgba(68, 170, 213, 0.1)',
                label: {
                    text: 'Normal',
                    style: {
                        color: '#606060'
                    }
                }
            }, { 
                from: 60,
                to: 90,
                color: 'rgba(255, 0, 0, 0.1)',
                label: {
                    text: 'Warning',
                    style: {
                        color: '#606060'
                    }
                }
            }, { 
                from: 90,
                to: 100,
                color: 'rgba(255, 0, 0, 0.4)',
                label: {
                    text: 'Alarming',
                    style: {
                        color: '#606060'
                    }
                }
            }]
        },
        tooltip: {
            valueSuffix: ' %'
        },
        plotOptions: {
            spline: {
                lineWidth: 4,
                states: {
                    hover: {
                        lineWidth: 5
                    }
                },
                marker: {
                    enabled: false
                },
                pointInterval: 15000, // time interval
                pointStart: Date.parse(dateT)
            }
        },
        series: [{
            name: 'VM',
            data: []
        }],
        navigation: {
            menuItemStyle: {
                fontSize: '10px'
            }
        }
      });
      }
      );
      //this function will add the given value to the chart
        function chartFunction(util) {
        if(util != undefined){
          var chart = $('#graph').highcharts();
          if(chart.series[0].data.length < 30){
            chart.series[0].addPoint(util);
          }else{
            chart.series[0].addPoint(util, true, true, true);
          }
          var pie = $('#pie').highcharts();
          pie.series[0].setData([util,100 - util]);
        }
      };
        //make a get call with the uuid present in the url to get new information
        var updateFunction = function(){
          $.ajax({
                 type: "GET",
                 url: "${pageContext.request.contextPath}/mininoc/ajax/instance/"+document.getElementById("uuid").innerHTML,
                 dataType: "json",
                 crossDomain:true,
                 success: function(jsonObj) {
                 //document.getElementById("uuid").innerHTML = jsonObj.uuid;
                 //document.getElementById("vmname").innerHTML = jsonObj.vmName;
                 //document.getElementById("tenant").innerHTML = jsonObj.tenantName;
                 if(jsonObj.cpuUtil != -1){
                    document.getElementById("usage").innerHTML = parseInt(jsonObj.cpuUtil);
                    chartFunction(parseInt(jsonObj.cpuUtil));
                  }
                },
                error: function(xhr) {
                  alert("Could not update for UUID: " + UUID + ". An error occured: " + xhr.status + " " + xhr.statusText);
                }
             });
        }
      var interval = 1000 * 15;
      var setIntervalID = setInterval(updateFunction, interval);
      $("#setIntervalBtn").click(function(){
        clearInterval(setIntervalID);
        var X = document.getElementById("timer").value;
        if(X == "" || X == null){
        X = 30;
        }
        interval = 1000 * X;
        setIntervalID = setInterval(updateFunction, interval);
      });
      }); 		
    </script>
    <style type="text/css">
      body{
      margin-top: 1%;
      margin-bottom: 1%;
      margin-left: 5%;
      margin-right: 5%;
      }
      td{
      padding: 1%;
      }
      h1{
      padding-bottom: 3%;
      }
      .inlineP{
        display: inline;
      }
    </style>
  </head>
  <body>
  <%
  String y;
  String m;
  String d;
  String h;
  String min;
  String s;
  String dateTemp;
  %>
  <%
  try{
	  SimpleDateFormat normalDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	  normalDate.setTimeZone(TimeZone.getTimeZone("UTC"));
	  	SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	  	SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	  	SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
	  	SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
	  	SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
	  	SimpleDateFormat secondFormat = new SimpleDateFormat("ss");
	  	Date date = new Date();
	  	y = yearFormat.format(date);
	  	m = monthFormat.format(date);
	  	d = dayFormat.format(date);
	  	h = hourFormat.format(date);
	  	min = minuteFormat.format(date);
	  	s = secondFormat.format(date);
	  	dateTemp = normalDate.format(date);
  	}catch(Exception e){
  		y = "2015";
  		m = "12";
  		d = "11";
  		h = "11";
  		min = "0";
  		s = "0";
  		dateTemp = "2015/12/11 11:00:00";
  	}
  %>
  	<input type="hidden" id="year" value="<%=y%>">
  	<input type="hidden" id="month" value="<%=m%>">
  	<input type="hidden" id="day" value="<%=d%>">
  	<input type="hidden" id="hour" value="<%=h%>">
  	<input type="hidden" id="minute" value="<%=min%>">
  	<input type="hidden" id="second" value="<%=s%>">
  	<input type="hidden" id="normalDate" value="<%=dateTemp%>">
    <center>
      <table width="100%">
        <tr>
          <td width="60%">
            <script src="http://code.highcharts.com/highcharts.js"></script>
            <script src="http://code.highcharts.com/modules/exporting.js"></script>
            <div id="pie" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
          </td>
          <td width="40%">
            <table width="100%">
              <thead>
                <h3>VM Information</h3>
              </thead>
              <tr>
                <td>
                  <h4 class="inlineP">Name: </h4>
                  <h4 id="vmname" class="inlineP">
                  <%= request.getParameter("vmName")%>
                  </h4>
                </td>
                <td></td>
              </tr>
              <tr>
                <td>
                  <h4 class="inlineP">UUID: </h4>
                  <h4 id="uuid" class="inlineP">
                  	<%= request.getAttribute("UUID")%>
                  </h4>
                </td>
                <td></td>
              </tr>
              <tr>
                <td>
                  <h4 class="inlineP">Tenant Name: </h4>
                  <h4 id="tenant" class="inlineP">
                  <%= request.getParameter("tenantName")%>
                  </h4>
                </td>
                <td></td>
              </tr>
              <tr>
                <td>
                  <h4 class="inlineP">Usage: </h4>
                  <h4 id="usage" class="inlineP">
                  <%= request.getParameter("cpuUtil")%>%
                  </h4>
                </td>
                <td></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table width="100%">
        <tr>
          <td width="100%">
            <div id="graph" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
          </td>
        </tr>
      </table>
      Please Enter the timer value (Default 15 Seconds): 
      <input type="text" id="timer" min="15" max="300" placeholder="15-300 Seconds." required>
      </input>
      <input type="button" id="setIntervalBtn" value="Set Timer"></input>
    </center>
  </body>
</html>