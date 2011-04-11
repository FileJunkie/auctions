<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
	<div id="userList">
	<s:if test="result.size() > 0">
		<div class="content">
			<table class="userTable" cellpadding="5px">
				<tr class="even">
					<th>Email</th>
					<th>Category</th>
					<th>Type</th>
				</tr>
				<s:iterator value="result" status="userStatus">
					<tr
						class="<s:if test="#userStatus.odd == true ">odd</s:if><s:else>even</s:else>">
						<td><s:property value="email" /></td>
						<td><s:property value="category" /></td>
						<td><s:property value="type" /></td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</s:if>
	</div>
</body>
</html>