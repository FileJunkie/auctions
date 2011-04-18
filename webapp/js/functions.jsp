<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="j" uri="/struts-jquery-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<j:head jqueryui="true" jquerytheme="humanity" />

<script type="text/javascript">
	
	function beforeSubmit() {
		if (!ismail()) {
			$('#email').addClass('ui-state-error')
					.toggle('shake', {}, 150);
			return false;
		}
		$('#email').removeClass('ui-state-error');
		if (!confirmPass()) {
			$('#pass').addClass('ui-state-error')
					.toggle('shake', {}, 150);
			$('#confPass').addClass('ui-state-error')
					.toggle('shake', {}, 150);
			return false;
		}
		$('#pass').removeClass('ui-state-error');
		$('#confPass').removeClass('ui-state-error');
		$('#register_form').submit();
	}

	function ismail() {
		var email = document.getElementById('email').value;
		emailTest = "^[_\\.0-9a-z-]+@([0-9a-z][0-9a-z_-]+\\.)+[a-z]{2,4}$";
		var regex = new RegExp(emailTest);
		if (regex.test(email)) {
			return true;
		} else {
			return false;
		}
	}
	
	function confirmPass() {
		var pass = document.getElementById('pass').value;
		var confPass = document.getElementById('confPass').value;
		if ((pass == confPass) && (jQuery.trim(pass).length != 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	function login() {
		$('#login_form').submit();
	}
	
	function ban() {
		$('#ban_form').submit();
	}
</script>