<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
	<br>
	<div id="itemCategories">
	<s:if test="%{result.size() > 0}">
		<div class="content">
			<table class="userTable" cellpadding="5px">
				<tr class="even">
					<th>Category</th>
				</tr>
				<s:iterator value="result" status="userStatus">
					<tr
						class="<s:if test="#userStatus.odd == true ">odd</s:if><s:else>even</s:else>">
						<td><s:property/></td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</s:if>
	</div>
</body>
</html>