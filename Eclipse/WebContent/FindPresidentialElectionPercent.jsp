<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Presidential Election Percent Data Entry</title>
</head>
<body>
	<form action="findpresidentialElectionPercent" method="post">
		<h1>Search for a PresidentialElectionPercent Entry by Year</h1>
		<p>
			<label for="year">Date (yyyy)</label> <input id="date" name="date"
				value="${fn:escapeXml(param.date)}">
		</p>
		<p>
			<input type="submit"> <br />
			<br />
			<br /> <span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br />
	<div id="presidentialElectionPercentcreate">
		<a href="presidentialElectionPercentcreate">Create Election
			Percentage Entry</a>
	</div>
	<div id="presidentialElectionPercentdelete">
		<a href="presidentialElectionPercentdelete">Delete Election
			Percentage Data Entry</a>
	</div>
	<div id="presidentialElectionPercentupdate">
		<a href="presidentialElectionPercentupdate">Update Election
			Percentage Data Entry</a>
	</div>
	<br />
	<h1>Matching PresidentialElectionPercent Data Entry</h1>
	<table border="1">
		<tr>
			<th>County Name</th>
			<th>Year</th>
			<th>Democrats</th>
			<th>Republicans</th>
			<th>Other</th>
		</tr>
		<c:forEach items="${presidentialElectionPercent}"
			var="presidentialElectionPercent">
			<tr>
				<td><c:out
						value="${presidentialElectionPercent.getCounty().getCountyName()}" /></td>
				<td><c:out value="${presidentialElectionPercent.getYear()}" /></td>
				<td><c:out
						value="${presidentialElectionPercent.getDemocratsPercent}" /></td>
				<td><c:out
						value="${presidentialElectionPercent.getRepublicansPercent()}" /></td>
				<td><c:out
						value="${presidentialElectionPercent.getOtherPercent()}" /></td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<br />
	<div id="findcovidbydate">
		<a href="findcovidbydate">Find COVID-19 Data Entry</a>
	</div>
	<div id="findpresidentialElectionPercent">
		<a href="findpresidentialElectionPercent">Find
			presidentialElectionPercent Data Entry</a>
	</div>
	<div id="findcounty">
		<a href="findcounty">Find County</a>
	</div>
	<div id="findstate">
		<a href="findstate">Find State</a>
	</div>
	<div id="findstategovernor">
		<a href="findstategovernor">Find State Governor List</a>
	</div>
	<div id="statehospitaldatacreate">
		<a href="statehospitaldatacreate">Create a State Hospital Data
			Entry</a>
	</div>
	<div id="countyhospitalcreate">
		<a href="countyhospitalcreate">Create a State Hospital Data Entry</a>
	</div>
	<div id="presidentialElectionPercentcreate">
		<a href="presidentialElectionPercentcreate">Create a
			PresidentialElectionPercent Entry</a>
	</div>
	<div id="presidentialelectionpercentagecreate">
		<a href="presidentialelectionpercentagecreate">Create a
			Presidential Election Percentage Entry</a>
	</div>

</body>
</html>
