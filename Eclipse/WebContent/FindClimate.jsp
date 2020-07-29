<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Climate</title>
</head>
<body>
	<form action="findclimate" method="post">
		<h1>Search for a Climate by County Name </h1>
		<p>
			<label for="countyname">County Name</label>
			<input id="countyname" name="countyname" value="${fn:escapeXml(param.countyname)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	  <br/><br/>
	<div id="climatecreate"><a href="climatecreate">Create State Governor Entry</a></div>
	<div id="climatedelete"><a href="climatedelete">Delete State Governor List</a></div>
	<div id="climateupdate"><a href="climateupdate">Update State Governor Entry</a></div>
	<br/>
	<h1>Matching List of Climates</h1>
        <table border="1">
            <tr>
            	<th>County</th>
                <th>Year</th>
				<th>Elevation</th>
                <th>WinterPrcp</th>
                <th>SummerPrcp</th>
                <th>SpringPrcp</th>
                <th>AutumnPrcp</th>
                <th>WinterTavg</th>
                <th>SummerTavg</th>
                <th>SpringTavg</th>
                <th>AutumnTavg</th>
            </tr>
					<c:forEach items="${climates}" var="climate" >
			<tr>
				<td><c:out value="${climate.getCounty().getCountyName()}" /></td>
				<td><c:out value="${climate.getYear()}" /></td>
				<td><c:out value="${climate.getElevation()}" /></td>
				<td><c:out value="${climate.getWinterPrcp()}" /></td>
				<td><c:out value="${climate.getSummerPrcp()}" /></td>
				<td><c:out value="${climate.getSpringPrcp()}" /></td>
				<td><c:out value="${climate.getAutumnPrcp()}" /></td>
				<td><c:out value="${climate.getWinterTavg()}" /></td>
				<td><c:out value="${climate.getSummerPrcp()}" /></td>
				<td><c:out value="${climate.getSpringPrcp()}" /></td>
				<td><c:out value="${climate.getAutumnPrcp()}" /></td>
			</tr>
					</c:forEach>
       </table>
	<br/><br/>
       <div id="findcovidbydate"><a href="findcovidbydate">Find COVID-19 Data Entry</a></div>
       <div id="findcounty"><a href="findcounty">Find County</a></div>
       <div id="findstate"><a href="findstate">Find State</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
       <div id="statehospitaldatacreate"><a href="statehospitaldatacreate">Create a State Hospital Data Entry</a></div>
       <div id="countyhospitalcreate"><a href="countyhospitalcreate">Create a State Hospital Data Entry</a></div>
       <div id="populationcreate"><a href="populationcreate">Create a Population Entry</a></div>
       <div id="presidentialelectionpercentagecreate"><a href="presidentialelectionpercentagecreate">Create a Presidential Election Percentage Entry</a></div>
</body>
</html>
