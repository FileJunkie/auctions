<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String email = (String) session.getAttribute("email");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Page</title>
<s:head />
<style type="text/css">
@import url(style.css);
</style>
</head>
<body>
	${email}
	<br>
	<div id="userList">
		<s:if test="%{result.size() > 0}">
			<div class="content">
				<table class="userTable" cellpadding="5px">
					<tr class="even">
						<s:hidden name="id" />
						<th>Email</th>
						<th>Category</th>
						<th>Type</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
					<s:iterator value="result" status="stat">
						<tr
							class="<s:if test="#stat.odd == true ">odd</s:if><s:else>even</s:else>">
							<td><s:property value="email" />
							</td>
							<td><s:property value="category" />
							</td>
							<td><s:property value="type" />
							</td>
							<td><s:url id="editURL" action="editUser">
									<s:param name="id" value="%{id}"></s:param>
								</s:url> <s:a href="%{editURL}">Edit</s:a></td>
							<td><s:url id="deleteURL" action="deleteUser">
									<s:param name="id" value="%{id}"></s:param>
								</s:url> <s:a href="%{deleteURL}">Delete</s:a></td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</s:if>
	</div>
</body>
</html>