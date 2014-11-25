<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(document).ready(function() {
		document.f.j_username.focus();
	});
</script>
<div id="main">
	<div id="box">
		<h4>Login with Username and Password</h4>
		<c:if test="${param.error != null}">
			<span class="loginerror"> Login failed. Please re-enter your
				username and password. </span>
		</c:if>

		<form name='f'
			action='${pageContext.request.contextPath}/j_spring_security_check'
			method='POST'>
			<table id="table">
				<tr>
					<td class="label">Username:</td>
					<td><input class="login" type='text' name='j_username'
						value=''></td>
				</tr>
				<tr>
					<td class="label">Password:</td>
					<td><input class="login" type='password' name='j_password' /></td>
				</tr>
				<tr>
					<td class="label">Remember me?</td>
					<td><input class="login" type='checkbox'
						name='_spring_security_remember_me' checked="checked" /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="Login" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="smallbox">
		<p>
			No account yet?&nbsp;&nbsp;<a href="<c:url value="/newuser"/>">Create
				account</a>
		</p>
	</div>
</div>
