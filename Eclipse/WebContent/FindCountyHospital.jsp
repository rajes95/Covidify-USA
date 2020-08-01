<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a County Hospital</title>
</head>
<body>
	<form action="findcountyhospital" method="post">
		<h1>Search for a County Hospital by County Name and State Name</h1>
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
	<div id="countyhospitalcreate"><a href="countyhospitalcreate">Create County Hospital Entry</a></div>
	<div id="countyhospitaldelete"><a href="countyhospitaldelete">Delete County Hospital List</a></div>
	<br/>
	<h1>Matching List of County Hospital Entries</h1>
        <table border="1">
            <tr>
            	<th>County</th>
                <th>Year</th>
				<th>ICUBeds</th>
            </tr>
					<c:forEach items="${countyhospitals}" var="countyhospital" >
			<tr>
				<td><c:out value="${countyhospital.getCounty().getCountyName()}" /></td>
				<td><c:out value="${countyhospital.getYear()}" /></td>
				<td><c:out value="${countyhospital.getIcuBeds()}" /></td>
			</tr>
					</c:forEach>
       </table>
	<br/><br/>
      
  <div id="findcovidbydate"><a href="findcovidbydate">Find COVID-19 Data Entry</a></div>
  <div id="findcovidbyrace"><a href="findcovidbyrace">Find COVID-19 By Race Data Entry</a></div>
       <div id="findcounty"><a href="findcounty">Find County</a></div>
       <div id="findstate"><a href="findstate">Find State</a></div>
       <div id="findclimate"><a href="findclimate">Find Climate</a></div>
       <div id="findstategovernor"><a href="findstategovernor">Find State Governor List</a></div>
       <div id="findstatehospitaldata"><a href="findstatehospitaldata">Find a State Hospital Data Entry</a></div>
       <div id="findcountyhospital"><a href="findcountyhospital">Find a County Hospital Data Entry</a></div>
       <div id="findpopulation"><a href="findpopulation">Find a Population Entry</a></div>
       <div id="findpresidentialelectionpercent"><a href="findpresidentialelectionpercent">Find a Presidential Election Percentage Entry</a></div>
       <div id="finddemogrpahic"><a href="finddemographic">Find COVID-19 By Demographic Data Entry</a></div>
</body>
</html>
