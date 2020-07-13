<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete a List of State Governors</title>
</head>
<body>
	<h1>${messages.title}</h1>
	<form action="stategovernordelete" method="post">
		<p>
		<p>
			<div <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
				<label for="statename">State Name</label>
				<input id="statename" name="statename" value="${fn:escapeXml(param.statename)}">
		</p>	
		<p>
			<label for="year">Year</label>
			<input id="year" name="year" value="${fn:escapeXml(param.year)}">
		</p>
			</div>
		</p>
		<p>
			<span id="submitButton" <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
			<input type="submit">
			</span>
		</p>
	</form>
	<br/><br/>
	<div id="stategovernorcreate"><a href="stategovernorcreate">Create State Governor Entry</a></div>
	<div id="stategovernordelete"><a href="stategovernordelete">Delete State Governor List</a></div>
	<div id="stategovernorupdate"><a href="stategovernorupdate">Update State Governor Entry</a></div>
	<br/><br/>
       <div id="findcovidbydate"><a href="findcovidbydate">Find COVID-19 Data Entry</a></div>
       <div id="findcounty"><a href="findcounty">Find County</a></div>
       <div id="findstate"><a href="findstate">Find State</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
</body>
</html>