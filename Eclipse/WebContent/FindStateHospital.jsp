<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a State Hospital Data Entry</title>
</head>
<body>
	<form action="findstateHospital" method="post">
		<h1>Search for a State Hospital Entry by Year</h1>
		<p>
			<label for="year">Date (yyyy)</label>
			<input id="date" name="date" value="${fn:escapeXml(param.date)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="stateHospitalcreate"><a href="stateHospitalcreate">Create COVID-19 Data Entry</a></div>
	<div id="stateHospitaldelete"><a href="stateHospitaldelete">Delete COVID-19 Data Entry</a></div>
	<div id="stateHospitalupdate"><a href="stateHospitalupdate">Update COVID-19 Data Entry</a></div>
	<br/>
	<h1>Matching State Hospital Data Entry</h1>
        <table border="1">
            <tr>
                <th>State Name</th>
				<th>Year</th>
                <th>Number of Hospitals</th>
                <th>Number of Hospital Employees</th>
            </tr>
                <tr>
					<td><c:out value="${stateHospital.getState().getStateName()}" /></td>
					<td><c:out value="${stateHospital.getYear()}" /></td>
					<td><c:out value="${stateHospital.getNumberOfHospitals}" /></td>
					<td><c:out value="${stateHospital.getNumberOfHospitalEmployees()}" /></td>
                </tr>

       </table>
		<br/><br/>
       <div id="findcovidbydate"><a href="findcovidbydate">Find COVID-19 Data Entry</a></div>
       <div id="findstateHospital"><a href="findstateHospital">Find stateHospital  Data Entry</a></div>
       <div id="findcounty"><a href="findcounty">Find County</a></div>
       <div id="findstate"><a href="findstate">Find State</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
       <div id="statehospitaldatacreate"><a href="statehospitaldatacreate">Create a State Hospital Data Entry</a></div>
       <div id="countyhospitalcreate"><a href="countyhospitalcreate">Create a State Hospital Data Entry</a></div>
       <div id="presidentialelectionpercentagecreate"><a href="presidentialelectionpercentagecreate">Create a Presidential Election Percentage Entry</a></div>
       
</body>
</html>
