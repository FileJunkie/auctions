<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="j" uri="/struts-jquery-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<j:head jqueryui="true" jquerytheme="humanity"/>

<script type="text/javascript">
	$(document).ready(function() {

		$('#register').button({ icons: {primary:'ui-icon-check'} }).click(function() {
			$('#register_form').submit();
		});

	});
</script>