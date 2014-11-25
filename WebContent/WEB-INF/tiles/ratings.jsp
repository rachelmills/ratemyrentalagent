<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="wrapper">
	<div id="main">

		<table id="table">
			<tr>
				<th>Agency Name</th>
				<th>Rating (out of 5)</th>
			</tr>
			<c:choose>
				<c:when test="${display eq ('user')}">
					<c:forEach var="rating" items="${ratings}">
						<tr>
							<td id="first"><c:out value="${rating.agent.agentName}"></c:out></td>
							<td id="last"><c:out value="${rating.rating}"></c:out></td>
							<td id="other"><a
								href="${pageContext.request.contextPath}/editrating?agentid=${rating.agent.id}&userid=${id}">Edit
									or delete</a></td>

						</tr>

					</c:forEach>
				</c:when>

				<c:when test="${display eq ('agent')}">
					<tr>
						<td id="first">Average</td>
						<td id="last"><c:out value="${averageRating}"></c:out></td>
						<td id="other"><a
							href="${pageContext.request.contextPath}/createrating?agentid=${id}">Rate
								this agent</a></td>
					</tr>
				</c:when>
			</c:choose>
		</table>

	</div>
</div>