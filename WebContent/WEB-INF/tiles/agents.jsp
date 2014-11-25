<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:out value="${name}"></c:out>

<div id="main">
	<div id="widebox">
		<c:if test="${agentName ne null}">
		<p>You searched for an agent named ${agentName}.</p>
		</c:if>
		<c:choose>
			<c:when test="${agents.size() == 0}">
				<p>We have no agents of that name. Please add the agent or
					search again.</p>
				<sec:authorize access="isAuthenticated()">

					<a href="${pageContext.request.contextPath}/createagent">Add
						agent</a>
				</sec:authorize>
				<a href="${pageContext.request.contextPath}/">Search</a>
			</c:when>
			<c:when test="${agents.size() gt 0}">
				<table id="table">
					<tr>
						<th>Name</th>
						<th>Suburb</th>
					</tr>
					<c:forEach var="agent" items="${agents}">
						<tr>
							<td><c:out value="${agent.agentName}"></c:out></td>
							<td><c:out value="${agent.suburb}"></c:out></td>
							<td id="other"><a
								href="${pageContext.request.contextPath}/ratings?agentid=${agent.id}">View
									ratings</a></td>
							<td id="other"><a
								href="${pageContext.request.contextPath}/createrating?agentid=${agent.id}">Rate
									this agent</a></td>
						</tr>
					</c:forEach>
				</table>
				<p>Please add an agent if the one your are searching for is not
					listed.</p>
				<a href="${pageContext.request.contextPath}/createagent">Add
					agent</a>
			</c:when>
		</c:choose>
	</div>
</div>
