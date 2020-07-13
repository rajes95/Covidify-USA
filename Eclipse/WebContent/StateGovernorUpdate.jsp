<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update a List of State Governors</title>
</head>
<body>
	<h1>Update List of State Governors</h1>
	<form action="stategovernorupdate" method="post">
		<p>
			<label for="stategovernorname">County Name</label>
			<input id="stategovernorname" name="stategovernorname" value="${fn:escapeXml(param.stategovernorname)}">
			<label for="statename">State Name</label>
			<input id="statename" name="statename" value="${fn:escapeXml(param.statename)}">
		</p>
		<p>
			<label for="date">Date (yyyy-mm-dd)</label>
			<input id="date" name="date" value="${fn:escapeXml(param.date)}">
		</p>
		<p>
			<label for="coviddeaths">New Number of COVID-19 Deaths</label>
			<input id="coviddeaths" name="coviddeaths" value="">
		</p>
		<p>
			<label for="covidcases">New Number of COVID-19 Cases</label>
			<input id="covidcases" name="covidcases" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>