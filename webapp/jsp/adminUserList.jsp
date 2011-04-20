<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String email = (String) session.getAttribute("email");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Userlist</title>
<s:head />
<style type="text/css">
@import url(style.css);
</style>
</head>
<body style="background-image: url(images/bg.jpg)">
	<div align="center"><img alt="Auction" src="images/aucHead.png" align="middle"></div><br/>
	
	<div align="right" style="padding-right: 30px">
		<h3>${email}</h3>
		<s:url id="logout" action="logout" escapeAmp="false"></s:url>
		<s:a href="%{logout}"><img src="images/auc_exit.png" border="0"></s:a>
	</div>
	<div>
		<div style="float: left; width: 250px;">
			<a href="index.jsp"><img src="images/auc_main.png" ALIGN="middle" border="0" style="padding-left: 30px"></a>
			
			<s:url id="userRoom" action="userRoom" escapeAmp="false"></s:url>
			<s:a href="%{userRoom}"><img src="images/auc_room.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<s:url id="items" action="itemsList" escapeAmp="false"></s:url>
			<s:a href="%{items}"><img src="images/auc_all_lots.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<s:url id="admin" action="adminConsole" escapeAmp="false"></s:url>
			<s:a href="%{admin}"><img src="images/auc_admin.png" ALIGN="middle" border="0" style="padding-left: 25px"></s:a> 
		</div>
		<br>
		<div id="userList" style="margin-left: 250px">
			<s:if test="%{result.size() > 0}">
				<div>
					<table cellpadding="5px">
						<tr class="even">
							<s:hidden name="id" />
							<th>Email</th>
							<th>Category</th>
							<th>Type</th>
							<th>Edit</th>
							<th>Delete</th>
						</tr>
						<s:iterator value="result" status="stat">
							<tr>
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
	</div>
</body>
</html>