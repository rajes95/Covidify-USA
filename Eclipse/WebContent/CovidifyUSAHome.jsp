<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Covidify USA</title>
</head>
<body>
	<form action="covidifyhome" method="post">
		<h1>Welcome to Covidify USA</h1>
		
		<h5>Press to Load Table</h5>
			<input type="submit">
	
		<br/>
			<span id="successMessage"><b>${messages.success}</b></span>
	</form>
	<br/>
	<h2>States Ordered by # COVID-19 Cases</h2>
        <table border="1">
            <tr>      
            	<th>Date</th>
                
				<th>Number of COVID-19 Cases</th>
                
				<th>State Name</th>
          
            </tr>
                	<c:forEach items="${topcases}" var="topcases" >
				<tr>
					<td><fmt:formatDate value="${topcases.getDate()}" pattern="MM-dd-yyyy hh:mm:sa"/></td>
					<td><c:out value="${topcases.getCovidCases()}" /></td>
					<td><c:out value="${topcases.getState().getStateName()}" /></td>
					
                </tr>
                
					</c:forEach>
		
       </table>
		<br/><br/>
  	   <div id="covidifyhome"><a href="covidifyhome">Covidify USA Home</a></div>
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