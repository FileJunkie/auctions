<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellpadding="5px">
	<s:iterator value="bidList" status="stat">
		<tr>
			<td><s:property value="userID" /></td>
			<td><s:property value="amount" /></td>
			<td>${time}</td>
		</tr>
	</s:iterator>
</table>