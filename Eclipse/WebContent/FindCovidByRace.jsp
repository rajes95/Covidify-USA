<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a COVID-19 Data by Race Entry</title>
</head>
<body>
	<form action="findcovidbydate" method="post">
		<h1>Search for a COVID-19 Race Data Entry by State Name</h1>
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
	<div id="covidbyracecreate"><a href="covidbyracecreate">Create COVID-19 Data by Race Entry</a></div>
	<div id="covidbyracedelete"><a href="covidbyracedelete">Delete COVID-19 Data by Race Entry</a></div>
	<div id="covidbyraceupdate"><a href="covidbyraceupdate">Update COVID-19 Data by Race Entry</a></div>
	<br/>
	<h1>Matching COVID-19 Data by Race Entry</h1>
        <table border="1">
            <tr>
				<th>State Name</th>
				<th>Race</th>
                <th>Date</th>
                <th>Positive cases</th>
				<th>Negative cases</th>
				<th>Deaths</th>
            </tr>
                <tr>
					<td><c:out value="${covidbyrace.getState().getStateName()}" /></td>
					<td><c:out value="${covidbyrace.getRaceType()}" /></td>
					<td><fmt:formatDate value="${covidbyrace.getDate()}" pattern="MM-dd-yyyy hh:mm:sa"/></td>
					<td><c:out value="${covidbyrace.getPositive()}" /></td>
					<td><c:out value="${covidbyrace.getNegative()}" /></td>
					<td><c:out value="${covidbyrace.getDeath()}" /></td>
                </tr>

       </table>
		<br/><br/>
       <div id="findcovidbydate"><a href="findcovidbydate">Find COVID-19 Data Entry</a></div>
       <div id="findcovidbyrace"><a href="findcovidbydrace">Find COVID-19 by Race Data Entry</a></div>
       <div id="findcounty"><a href="findcounty">Find County</a></div>
       <div id="findstate"><a href="findstate">Find State</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
       <div id="statehospitaldatacreate"><a href="statehospitaldatacreate">Create a State Hospital Data Entry</a></div>
       <div id="countyhospitalcreate"><a href="countyhospitalcreate">Create a State Hospital Data Entry</a></div>
       <div id="populationcreate"><a href="populationcreate">Create a Population Entry</a></div>
       <div id="presidentialelectionpercentagecreate"><a href="presidentialelectionpercentagecreate">Create a Presidential Election Percentage Entry</a></div>
       
</body>
</html>
