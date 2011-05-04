<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="j" uri="/struts-jquery-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<j:head jqueryui="true" jquerytheme="humanity" />

<script type="text/javascript">

$(document).ready(function(){
	setInterval("$('#bids').load('getBids');",1000); // По умолчанию время обновления 1 секунда.
	
	setInterval("$('#detailsItem').load('updateItemInfo');",6000); // По умолчанию время обновления 1 секунда.
}); 
	
</script>