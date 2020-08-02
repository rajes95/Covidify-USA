<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Climate Entry</title>
</head>
<body>
	<h1>Create a Climate Entry</h1>
	<form action="climatecreate" method="post">
		<p>
			<label for="countyname">County Name (Pre-existing)</label>
			<input id="countyname" name="countyname" value="${fn:escapeXml(param.countyname)}">
			<label for="statename">State Name (Pre-existing)</label>
			<input id="statename" name="statename" value="${fn:escapeXml(param.statename)}">
		</p>
		<p>
			<label for="year">Year</label>
			<input id="year" name="year" value="">
		</p>
		<p>
			<label for="elevation">Elevation</label>
			<input id="elevation" name="elevation" value="">
		</p>
		<p>
			<label for="winterprcp">Winter Precipitation</label>
			<input id="winterprcp" name="winterprcp" value="">
		</p>
		<p>
			<label for="summerprcp">Summer Precipitation</label>
			<input id="summerprcp" name="summerprcp" value="">
		</p>
		<p>
			<label for="springprcp">Spring Precipitation</label>
			<input id="springprcp" name="springprcp" value="">
		</p>
		<p>
			<label for="autumnprcp">Autumn Precipitation</label>
			<input id="autumnprcp" name="autumnprcp" value="">
		</p>
		<p>
			<label for="winterTavg">Avg. Winter Temperature</label>
			<input id="winterTavg" name="winterTavg" value="">
		</p>
		<p>
			<label for="summerTavg">Avg. Summer Temperature</label>
			<input id="summerTavg" name="summerTavg" value="">
		</p>
		<p>
			<label for="springTavg">Avg. Spring Temperature</label>
			<input id="springTavg" name="springTavg" value="">
		</p>
		<p>
			<label for="autumnTavg">Avg. Autumn Temperature</label>
			<input id="autumnTavg" name="autumnTavg" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
	<br/><br/>
	<div id="climatecreate"><a href="climatecreate">Create Climate Data Entry</a></div>
	<div id="climatedelete"><a href="climatedelete">Delete Climate Data</a></div>
	<br/><br/>
         <div id="findcovidbydate"><a href="findcovidbydate">Find COVID-19 Data Entry</a></div>
       <div id="findcovidbyrace"><a href="findcovidbyrace">Find COVID-19 By Race Data Entry</a></div>
       <div id="findmortalityrate"><a href="findmortalityrate">Find Mortality Rate Data</a></div>
       <div id="findcounty"><a href="findcounty">Find County</a></div>
       <div id="findstate"><a href="findstate">Find State</a></div>
       <div id="findclimate"><a href="findclimate">Find Climate</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
       <div id="findstatehospitaldata"><a href="findstatehospitaldata">Find a State Hospital Data Entry</a></div>
       <div id="findcountyhospital"><a href="findcountyhospital">Find a County Hospital Data Entry</a></div>
       <div id="findpopulation"><a href="findpopulation">Find a Population Entry</a></div>
       <div id="findpresidentialelectionpercent"><a href="findpresidentialelectionpercent">Find a Presidential Election Percentage Entry</a></div>
       <div id="finddemogrpahic"><a href="finddemographic">Find Demographics Data</a></div>
       
</body>
</html>