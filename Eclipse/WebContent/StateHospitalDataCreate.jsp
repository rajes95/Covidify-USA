<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a StateHospitalData Entry</title>
</head>
<body>
	<h1>Create a StateHospitalData Entry</h1>
	<form action="statehospitaldatacreate" method="post">
		<p>
			<label for="statename">State Name (Pre-existing)</label>
			<input id="statename" name="statename" value="${fn:escapeXml(param.statename)}">
		</p>
		<p>
			<label for="year">Year</label>
			<input id="year" name="year" value="">
		</p>
		<p>
			<label for="numOfHospitals">Number Of Hospitals</label>
			<input id="numOfHospitals" name="numOfHospitals" value="">
		</p>
		<p>
			<label for="numOfEmployees">Number Of Employees</label>
			<input id="numOfEmployees" name="numOfEmployees" value="">
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
       <div id="findcovidbydate"><a href="findcovidbydate">Find COVID-19 Data Entry</a></div>
       <div id="findcounty"><a href="findcounty">Find County</a></div>
       <div id="findstate"><a href="findstate">Find State</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
       
</body>
</html>