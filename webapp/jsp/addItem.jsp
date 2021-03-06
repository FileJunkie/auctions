<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="<s:url value="/style.css"/>"/>
<jsp:include page="/js/functions.jsp" />
<link type="text/css" href="/js/latest.css" rel="Stylesheet" />
<jsp:include page="/js/latest.jsp" />

<%
	Boolean isAdmin = (Boolean) session.getAttribute("admin");
	Boolean seller = (Boolean) session.getAttribute ("seller");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Item</title>
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
			<% if ((isAdmin != null) && (isAdmin)) { %>
			<s:url id="admin" action="adminConsole" escapeAmp="false"></s:url>
			<s:a href="%{admin}"><img src="images/auc_admin.png" ALIGN="middle" border="0" style="padding-left: 25px"></s:a> <!-- тут тоже ссылка в виде картинок -->
			<%}%>
		</div>
		<div id="addNewItem" style="margin-left: 250px"
			class="ui-widget ui-widget-content ui-state-active register">
			<form id="addItemForm" action="pushItem" method="post" enctype="multipart/form-data">
					<table cellpadding="3" cellspacing="0">
						<tr>
							<td>Category  </td>
							<td><s:select id="category" name="category" list="categoryList"
								headerKey="" headerValue="Select" label="Select a category"
									cssClass="text ui-state-default ui-corner-all" />
							</td>
						</tr>
						<tr>
							<td>Name  </td>
							<td><s:textfield id="name" name="name"
									cssClass="text ui-state-default ui-corner-all" />
							</td>
							<td id="lotName" style="color: red; display: none;"> is required! </td>
						</tr>
						<tr>
							<td>Description  </td>
							<td><s:textfield id="desc" name="description"
									cssClass="text ui-state-default ui-corner-all" />
							</td>
							<td id="lotDesc" style="color: red; display: none;"> is required! </td>
						</tr>
						<tr>
							<td>Photo  </td>
							<td><s:file size="10px" id="image" name="image" 
									cssClass="text ui-state-default ui-corner-all" />
							</td>
						</tr>
						<tr>
							<td>Type  </td>
							<td><s:radio name="type" list="auctionTypes" value="defaultTypeValue" 
									cssClass="text ui-state-default ui-corner-all" />
							</td>
						</tr>
						<tr>
							<td>Start bid  </td>
							<td><s:textfield id="startBid" name="startBid"
									cssClass="text ui-state-default ui-corner-all" />
							</td>
							<td id="start" style="color: red; display: none;"> is required! </td>
						</tr>
						<tr>
							<td>Min </td>
							<td><s:textfield id="min" name="min"
									cssClass="text ui-state-default ui-corner-all" />
							</td>
							<td id="lotMin" style="color: red; display: none;"> is required! </td>
						</tr>
						<tr>
							<td>Start registration </td>
							<td><s:textfield id="startReg" name="startReg"
									value="yyyy-mm-dd"
									cssClass="datepickerTimeField"/>
							</td>
						</tr>
						<tr>
							<td>Finish registration </td>
							<td><s:textfield id="finishReg" name="finishReg"
									value="yyyy-mm-dd"
									cssClass="datepickerTimeField"/>
							</td>
						</tr>
						<tr>
							<td>Start auction </td>
							<td><s:textfield id="startAuc" name="startAuc"
									value="yyyy-mm-dd"
									cssClass="datepickerTimeField"/>
							</td>
						</tr>
						<tr>
							<td>Finish auction </td>
							<td><s:textfield id="finishAuc" name="finishAuc"
									value="yyyy-mm-dd"
									cssClass="datepickerTimeField"/>
							</td>
						</tr>
						<tr>
							<td>Delivery date </td>
							<td><s:textfield id="delivery" name="delivery"
									value="yyyy-mm-dd"
									cssClass="datepickerTimeField"/>
							</td>
						</tr>
					</table>
				<div align="right" style="padding-right: 20px">
					<input type='button' class="ui-state-default ui-corner-all" value="Add" onclick='javascript:beforeItemSubmit();'>
				</div>
			</form>
		</div>
		</div>
	</body>
</html>