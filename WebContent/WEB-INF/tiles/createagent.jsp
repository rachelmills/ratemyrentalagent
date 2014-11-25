<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="main">
	<form:form method="POST"
		action="${pageContext.request.contextPath}/docreateagent"
		commandName="agent">
		<div id="widebox">
			<table id="table">
				<tr>
					<td class="label">Agent Name:</td>
					<td class="label"><form:input name="agentName" type="text"
							path="agentName" /><br /> <form:errors path="agentName"
							cssClass="error"></form:errors></td>
				</tr>
				<tr>
					<td class="label">Suburb:</td>
					<td class="label"><form:input name="suburb" type="text"
							path="suburb" /><br /> <form:errors path="suburb"
							cssClass="error"></form:errors></td>
				</tr>
				<tr>
					<td class="label"></td>
					<td class="label"><input type="submit" value="Add agent"></td>
				</tr>
			</table>
		</div>
	</form:form>
</div>