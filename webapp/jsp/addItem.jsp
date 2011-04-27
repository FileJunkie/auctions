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

<div id="addNewItem"
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
						</tr>
						<tr>
							<td>Description  </td>
							<td><s:textfield id="desc" name="description"
									cssClass="text ui-state-default ui-corner-all" />
							</td>
						</tr>
						<tr>
							<td>Photo  </td>
							<td><s:file id="image" name="image" 
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
						</tr>
						<tr>
							<td>Min </td>
							<td><s:textfield id="min" name="min"
									cssClass="text ui-state-default ui-corner-all" />
							</td>
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
					</table>
				<div align="right" style="padding-right: 20px">
					<input type='button' class="ui-state-default ui-corner-all" value="Add" onclick='javascript:beforeItemSubmit();'>
				</div>
		</form>
</div>