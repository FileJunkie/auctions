<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="j" uri="/struts-jquery-tags"%>
<j:head jqueryui="true" jquerytheme="humanity" />

<script type="text/javascript">

	function beforeSubmit() {
		
		if (!isValid()) {
			$('#name').addClass('ui-state-error')
						.toggle('shake', {}, 150);
			return false;
			$('#name').removeClass('ui-state-error');
		}
		$('#form').submit();
	}
	
	function isValid() {
		var name = document.getElementById('name').value; 
		if (name.length == 0) {
			return false;
		} else {
			return true;
		}
	}
</script>

<%
	Boolean seller = (Boolean) session.getAttribute ("seller");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Category</title>
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
			
			<s:url id="items" action="itemsList" escapeAmp="false"></s:url>
			<s:a href="%{items}"><img src="images/auc_all_lots.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<s:url id="userRoom" action="userRoom" escapeAmp="false"></s:url>
			<s:a href="%{userRoom}"><img src="images/auc_room.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<%
				if ((seller != null) && (seller)) {
			%>
			<s:url id="addItem" action="addItem" escapeAmp="false"></s:url>
			<s:a href="%{addItem}"><img src="images/auc_put_lot.png" ALIGN="middle" border="0" style="padding-left: 30px"></s:a>
			<%} %>
			<s:url id="admin" action="adminConsole" escapeAmp="false"></s:url>
			<s:a href="%{admin}"><img src="images/auc_admin.png" ALIGN="middle" border="0" style="padding-left: 25px"></s:a> 
		</div>
		<div id="editCategory"
			class="ui-widget ui-widget-content ui-state-active register" style="margin-left: 250px">
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
				<input type='button' class="ui-state-default ui-corner-all" value="Edit" onclick='javascript:beforeSubmit();'>
				</div>
			</s:form>
		</div>
		</div>
	</body>