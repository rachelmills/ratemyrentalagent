<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!--  <img src="${pageContext.request.contextPath}/static/images/jacaranda.jpg"/>-->
<div id="main">
	<div id="boxleft">
		<p id="justify">Rate My Rental Agent is a simple website to enable
			tenants to see ratings of rental agencies and create their own. The
			more ratings entered the more helpful this website will be, so please
			feel free to add ratings for agencies you may have used.</p>
		<p>** Simply search for an agent using the box below. Then follow
			the links to view previous ratings or add your own rating. **</p>
	</div>

	<form:form method="POST"
		action="${pageContext.request.contextPath}/search" commandName="agentSearch">

		<div id="box">
			<h3>Find a real estate agent</h3>
			<p>
				<form:input path="agentName" name="agentName" type="text"
					placeholder="Enter agency name" /><br /> <form:errors path="agentName"
							cssClass="error"></form:errors>
			</p>
			<input type="submit" value="SEARCH">

		</div>
	</form:form>
	<c:choose>
		<c:when test="${hasRatings}">
			<div id="smallbox">
				<p>
					View and edit your ratings&nbsp;&nbsp;<a
						href="<c:url value="/userratings?userid=${userid}"/>">here</a>
				</p>
			</div>
		</c:when>
	</c:choose>

</div>

