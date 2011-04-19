<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="<s:url value="/style.css"/>"/>
<jsp:include page="/js/functions.jsp" />

<div id="editCategory"
	class="ui-widget ui-widget-content ui-state-active register">
	<s:form id="form" action="updateCategory">
		<table cellpadding="3" cellspacing="0">
			<tr>
				<td>Category  </td>
				<td><s:textfield id="name" name="name"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
		</table>
		<div align="right" style="padding-right: 20px">
			<input type='button' class="ui-state-default ui-corner-all" value="Edit" onclick='javascript:submit();'>
		</div>
	</s:form>
</div>