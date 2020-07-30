<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Covid By Race Entry</title>
</head>
<body>
	<h1>Create Covid By Race Entry</h1>
	<form action="covidbyracecreate" method="post">
		<p>
			<label for="statename">State Name (Pre-existing)</label>
			<input id="statename" name="statename" value="${fn:escapeXml(param.statename)}">
		</p>
		<p>
			<label for="racetype">Race</label>
			<select id="racetype" name="racetype" >
				<option>White</option>
				<option>Black</option>
				<option>Hispanic</option>
				<option>Asian</option>
				<option>Multiracial</option>
				<option>NHPI</option>
				<option>Other</option>
				<option>Unknown</option>
			</select>
		</p>
		<p>
			<label for="positive">Positive</label>
			<input id="positive" name="positive" value="">
		</p>
		<p>
			<label for="negative">Negative</label>
			<input id="negative" name="negative" value="">
		</p>
		<p>
			<label for="death">Deaths</label>
			<input id="death" name="death" value="">
		</p>
		<p>
			<label for="date">Date (yyyy-mm-dd)</label>
			<input id="date" name="date" value="${fn:escapeXml(param.date)}">
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
       <div id="findclimate"><a href="findclimate">Find Climate</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
       <div id="findstatehospitaldata"><a href="findstatehospitaldata">Find a State Hospital Data Entry</a></div>
       <div id="findcountyhospital"><a href="findcountyhospital">Find a State Hospital Data Entry</a></div>
       <div id="findpopulation"><a href="findpopulation">Find a Population Entry</a></div>
       <div id="findpresidentialelectionpercent"><a href="findpresidentialelectionpercent">Find a Presidential Election Percentage Entry</a></div>
</body>
</html>