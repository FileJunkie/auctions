<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="<s:url value="/style.css"/>"/>
<jsp:include page="/js/functions.jsp" />

<div id="editUser"
	class="ui-widget ui-widget-content ui-state-active register">
	<s:form id="register_form" action="updateUser">
		<table cellpadding="3" cellspacing="0">
			<tr>
				<td>Email  </td>
				<td><s:textfield id="email" name="email"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Password  </td>
				<td><s:password id="pass" name="password"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Confirm password  </td>
				<td><s:password id="confPass"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Category  </td>
				<td><s:select name="category" list="categoriesLabels"
						headerKey="" headerValue="Select" label="Select a category"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Type  </td>
				<td><s:select name="type" list="typesLabels" headerKey=""
						headerValue="Select" label="Select a type"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Active  </td>
				<td><s:radio name="active" list="#{'true':'Yes','false':'No'}" value="false"
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
			<tr>
				<td>Admin  </td>
				<td><s:radio name="admin" list="#{'true':'Yes','false':'No'}" value="false" 
						cssClass="text ui-state-default ui-corner-all" />
				</td>
			</tr>
		</table>
		<div align="right" style="padding-right: 20px">
			<input type='button' class="ui-state-default ui-corner-all" value="Edit" onclick='jscript:beforeSubmit();'>
		</div>
	</s:form>
</div>