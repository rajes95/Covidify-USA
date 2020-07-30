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
		<h1>Search for a County by County and State Name Pair</h1>
		<p>
			<label for="countyname">County Name</label>
			<input id="countyname" name="countyname" value="${fn:escapeXml(param.countyname)}">
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
	 <br/><br/>
	<div id="countycreate"><a href="countycreate">Create County</a></div>
	<div id="countydelete"><a href="countydelete">Delete County</a></div>
	<div id="countyupdate"><a href="countyupdate">Update County</a></div>
	<br/>
	<h1>Matching County</h1>
        <table border="1">
            <tr>
                <th>County Name</th>
				<th>County FIPS</th>
                <th>Latitude</th>
                <th>Longitude</th>
            </tr>
                <tr>
                    <td><c:out value="${county.getCountyName()}" /></td>
					<td><c:out value="${county.getCountyFIPS()}" /></td>
					<td><c:out value="${county.getLatitude()}" /></td>
                    <td><c:out value="${county.getLongitude()}" /></td> 
                </tr>

       </table>
     
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
