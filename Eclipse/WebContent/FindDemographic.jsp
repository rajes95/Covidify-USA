<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Demographic</title>
</head>
<body>
	<form action="finddemographic" method="post">
		<h1>Search for a Demographic by County Name and State Name</h1>
		<p>
			<label for="countyname">County Name</label>
			<input id="countyname" name="countyname" value="${fn:escapeXml(param.countyname)}">
		</p>
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
	  <br/><br/>
	<div id="demographiccreate"><a href="demographiccreate">Create Demographic Entry</a></div>
	<div id="demographicdelete"><a href="demographicdelete">Delete Demographic List</a></div>
	<div id="demographicupdate"><a href="demographicupdate">Update Demographic Entry</a></div>
	<br/>
	<h1>Matching List of Demographics</h1>
        <table border="1">
            <tr>
            	<th>County</th>
                <th>Year</th>
				<th>White</th>
                <th>African American</th>
                <th>Latino</th>
                <th>Native American</th>
                <th>Asian American</th>
                <th>Other Ethnicity</th>
                <th>Poverty Rate</th>
                <th>Median Age</th>
                <th>Median Earnings</th>
            </tr>
					<c:forEach items="${demographics}" var="demographic" >
			<tr>
				<td><c:out value="${demographic.getCounty().getCountyName()}" /></td>
				<td><c:out value="${demographic.getYear()}" /></td>
				<td><c:out value="${demographic.getWhite()}" /></td>
				<td><c:out value="${demographic.getAfricanAmerican()}" /></td>
				<td><c:out value="${demographic.getLatino()}" /></td>
				<td><c:out value="${demographic.getNativeAmerican()}" /></td>
				<td><c:out value="${demographic.getAsianAmerican()}" /></td>
				<td><c:out value="${demographic.getOtherEthnicity()}" /></td>
				<td><c:out value="${demographic.getPovertyRate()}" /></td>
				<td><c:out value="${demographic.getMedianAge()}" /></td>
				<td><c:out value="${demographic.getMedianEarnings()}" /></td>
			</tr>
					</c:forEach>
       </table>
	<br/><br/>
       <div id="findcovidbydate"><a href="findcovidbydate">Find COVID-19 Data Entry</a></div>
       <div id="findcovidbyrace"><a href="findcovidbyrace">Find COVID-19 By Race Data Entry</a></div>
       <div id="findmortalityrate"><a href="findmortalityrate">Find Mortality Rate Data</a></div>
       <div id="findcounty"><a href="findcounty">Find County</a></div>
       <div id="findstate"><a href="findstate">Find State</a></div>
       <div id="findclimate"><a href="findclimate">Find Climate</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
       <div id="findstatehospitaldata"><a href="findstatehospitaldata">Find a State Hospital Data Entry</a></div>
       <div id="findcountyhospital"><a href="findcountyhospital">Find a County Hospital Data Entry</a></div>
       <div id="findpopulation"><a href="findpopulation">Find a Population Entry</a></div>
       <div id="findpresidentialelectionpercent"><a href="findpresidentialelectionpercent">Find a Presidential Election Percentage Entry</a></div>
       <div id="finddemogrpahic"><a href="finddemographic">Find Demographics Data</a></div>
       
</body>
</html>