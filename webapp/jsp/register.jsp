<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="<s:url value="/style.css"/>"/>
<jsp:include page="/js/functions.jsp" />

<!--<script type="text/javascript">
	$(document).ready(function() {
		$('#registration').dialog({
			width : 400,
			resizable : false,
			modal : true
		});
	});
</script>-->

<div id="registration"
	class="ui-widget ui-widget-content ui-state-active register">
	<s:form id="register_form" action="insertUser">
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
		</table>
		<div align="right" style="padding-right: 20px">
			<input type='button' class="ui-state-default ui-corner-all" value="Register" onclick='jscript:beforeSubmit();'>
		</div>
	</s:form>
</div>