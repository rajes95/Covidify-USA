<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Demographics Entry</title>
</head>
<body>
	<h1>Create a Demographics Entry</h1>
	<form action="demographiccreate" method="post">
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
			<label for="white">Percentage White</label>
			<input id="white" name="white" value="">
		</p>
		<p>
			<label for="africanamerican">Percentage African American</label>
			<input id="africanamerican" name="africanamerican" value="">
		</p>
		<p>
			<label for="latino">Percentage Latino</label>
			<input id="latino" name="latino" value="">
		</p>
		<p>
			<label for="nativeamerican">Percentage Native American</label>
			<input id="nativeamerican" name="nativeamerican" value="">
		</p>
		<p>
			<label for="asian">Percentage Asian American</label>
			<input id="asian" name="asian" value="">
		</p>
		<p>
			<label for="other">Percentage Other</label>
			<input id="other" name="other" value="">
		</p>
		<p>
			<label for="poverty">Poverty Rate</label>
			<input id="poverty" name="poverty" value="">
		</p>
		<p>
			<label for="medianage">Median Age</label>
			<input id="medianage" name="medianage" value="">
		</p>
		<p>
			<label for="medianearnings">Median Earnings</label>
			<input id="medianearnings" name="medianearnings" value="">
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
	<div id="demographiccreate"><a href="demographiccreate">Create Demographics Data Entry</a></div>
	<div id="demographicdelete"><a href="demographicdelete">Delete Demographics Data</a></div>
	<br/><br/>
     
       <div id="findcovidbydate"><a href="findcovidbydate">Find COVID-19 Data Entry</a></div>
       <div id="findcounty"><a href="findcounty">Find County</a></div>
       <div id="findstate"><a href="findstate">Find State</a></div>
       <div id="findclimate"><a href="findclimate">Find Climate</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
       <div id="findstatehospitaldata"><a href="findstatehospitaldata">Find a State Hospital Data Entry</a></div>
       <div id="findcountyhospital"><a href="findcountyhospital">Find a County Hospital Data Entry</a></div>
       <div id="findpopulation"><a href="findpopulation">Find a Population Entry</a></div>
       <div id="findpresidentialelectionpercent"><a href="findpresidentialelectionpercent">Find a Presidential Election Percentage Entry</a></div>
</body>
</html>