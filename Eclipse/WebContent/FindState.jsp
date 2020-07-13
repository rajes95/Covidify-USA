<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a State</title>
</head>
<body>
	<form action="findstate" method="post">
		<h1>Search for a State</h1>
		<p>
			<label for="statename">State Name</label>
			<input id="statename" name="statename" value="${fn:escapeXml(param.statename)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="stateCreate"><a href="statecreate">Create State</a></div>
	<br/>
	<h1>Matching State</h1>
        <table border="1">
            <tr>
                <th>State Name</th>
                <th>State FIPS</th>
                <th>Postal Code</th>
            </tr>
                <tr>
                    <td><c:out value="${state.getStateName()}" /></td>
                    <td><c:out value="${state.getStateFIPS()}" /></td>
                    <td><c:out value="${state.getPostalCode()}" /></td>
                </tr>

       </table>
</body>
</html>
