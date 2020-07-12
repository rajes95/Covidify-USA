<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update a State</title>
</head>
<body>
	<h1>Update State</h1>
	<form action="stateupdate" method="post">
		<p>
			<label for="statename">State Name</label>
			<input id="statename" name="statename" value="${fn:escapeXml(param.statename)}">
		</p>
		<p>
			<label for="stateFIPS">New State FIPS</label>
			<input id="stateFIPS" name="stateFIPS" value="">
		</p>
		<p>
			<label for="postalcode">New Postal Code</label>
			<input id="postalcode" name="postalcode" value="">
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