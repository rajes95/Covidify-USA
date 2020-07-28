<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Mortality Rates Entry</title>
</head>
<body>
	<h1>Create a Mortality Rates Entry</h1>
	<form action="mortalityratescreate" method="post">
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
			<label for="neonatal">Neonatal Disorders Mortality Rate</label>
			<input id="neonatal" name="neonatal" value="">
		</p>
		<p>
			<label for="hivaids">HIV, AIDS, TB Mortality Rate</label>
			<input id="hivaids" name="hivaids" value="">
		</p>
		<p>
			<label for="diabetes">Diabetes Urogenital Blood Endocrine Disease Mortality Rate</label>
			<input id="diabetes" name="diabetes" value="">
		</p>
		<p>
			<label for="chronicresp">Chronic Respiratory Diseases Mortality Rate</label>
			<input id="chronicresp" name="chronicresp" value="">
		</p>
		<p>
			<label for="liverdisease">Liver Disease Mortality Rate</label>
			<input id="liverdisease" name="liverdisease" value="">
		</p>
		<p>
			<label for="nutritionaldef">Nutritional Deficiencies Mortality Rate</label>
			<input id="nutritionaldef" name="nutritionaldef" value="">
		</p>
		<p>
			<label for="cardiovascular">Cardiovascular Disease Mortality Rate</label>
			<input id="cardiovascular" name="cardiovascular" value="">
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
       <div id="statehospitaldatacreate"><a href="statehospitaldatacreate">Create a State Hospital Data Entry</a></div>
       <div id="countyhospitalcreate"><a href="countyhospitalcreate">Create a State Hospital Data Entry</a></div>
       <div id="presidentialelectionpercentagecreate"><a href="presidentialelectionpercentagecreate">Create a Presidential Election Percentage Entry</a></div>
</body>
</html>