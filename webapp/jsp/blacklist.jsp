<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/js/functions.jsp" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Blacklist Page</title>
<s:head />
<style type="text/css">
@import url(style.css);
</style>
</head>
<body>
	<div id="newBan" class="register">
		<s:form id="ban_form" action="addBan">
			<table cellpadding="3" cellspacing="0">
				<tr>
					<td>Email</td>
					<td><s:textfield id="email" name="email" /></td>
					<td><input type='button' value="Add" onclick="javascript:submit();"></td>
				</tr>
			</table>
		</s:form>
	</div>
	<br>
	<div id="blacklist">
	<s:if test="%{result.size() > 0}">
		<div class="content">
			<table class="userTable" cellpadding="5px">
				<tr class="even">
					<s:hidden name="id"/>
					<th>Email</th>
					<th>Remove</th>
				</tr>
				<s:iterator value="result" status="userStatus">
					<tr
						class="<s:if test="#userStatus.odd == true ">odd</s:if><s:else>even</s:else>">
						<td><s:property value="email"/></td>
						<td><s:url id="remove" action="unban">
								<s:param name="id" value="%{id}"></s:param>
							</s:url> <s:a href="%{remove}">Remove</s:a>
						</td>
						</tr>
				</s:iterator>
			</table>
		</div>
	</s:if>
	</div>
</body>
</html>