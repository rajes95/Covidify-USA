<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a County</title>
</head>
<body>
	<form action="findcounty" method="post">
		<h1>Search for a County by CountyName and StateName</h1>
		<p>
			<label for="countyname">CountyName</label>
			<input id="countyname" name="countyname" value="${fn:escapeXml(param.countyname)}">
			<label for="statename">StateName</label>
			<input id="statename" name="statename" value="${fn:escapeXml(param.statename)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<h1>Matching County</h1>
        <table border="1">
            <tr>
                <th>CountyName</th>
                <th>Latitude</th>
                <th>Longitude</th>
            </tr>
                <tr>
                    <td><c:out value="${county.getCountyName()}" /></td>
                    <td><c:out value="${county.getLatitude()}" /></td>
                    <td><c:out value="${county.getLongitude()}" /></td> 
                </tr>

       </table>
</body>
</html>
