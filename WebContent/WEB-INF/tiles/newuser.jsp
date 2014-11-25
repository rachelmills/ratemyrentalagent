<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="main">
	<h2>Create new user</h2>
	<form:form id="details" method="POST"
		action="${pageContext.request.contextPath}/createuser"
		commandName="user">
		<div id="widebox">
			<table>
				<tr>
					<td class="label">Username:</td>
					<td class="label"><form:input name="username" type="text"
							path="username" /><br />
						<div class="error">
							<form:errors path="username"></form:errors>
						</div></td>
				</tr>
				<tr>
					<td class="label">Password:</td>
					<td class="label"><form:input name="password" type="password"
							path="password" id="password" /><br />
						<div class="error">
							<form:errors path="password"></form:errors>
						</div></td>
				</tr>
				<tr>
					<td class="label">Confirm Password:</td>
					<td class="label"><input name="confirmpassword"
						type="password" id="confirmpassword" />
						<div id="matchpasswords"></div></td>
				</tr>
				<tr>
					<td class="label"></td>
					<td class="label"><input type="submit" value="Create User"></td>
				</tr>
			</table>
		</div>
	</form:form>
</div>