package ru.spbstu.students.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.spbstu.students.dto.UserInfo;

public class Util {

	public static String getHashMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes("UTF-8"));
		byte[] digest = md.digest();

		StringBuffer sb = new StringBuffer(32);
		for (int i = 0; i < digest.length; i++) {
			byte b = digest[i];
			sb.append(String.format("%02x", b));
		}
		String md5 = sb.toString();
		return md5;
	}
	
	public static void sendActivationMail(String email) throws Exception {
		String subject = "Registration new user";
		String body = "Hello, " + email + "! To comlete the registration " +
				"you should go to this link: ";
		String sender = "group1721@gmail.com";
		String user = "group1721";
		String passw = ":vjnrfH4";
		
		try {
	        InetAddress addr = InetAddress.getLocalHost();
	        String hostname = addr.getHostName();
	        body += "http://" + hostname + ":8080/Auction/activation.action";
	    } catch (UnknownHostException e) {
	    	e.printStackTrace();
	    }
		
	    
		EMail.setLP(user, passw);
		EMail.sendMail(subject, body, sender, email);	
	}
	
	public static boolean isValidUser (UserInfo user) {
		if ((user.getEmail().trim().length() != 0) && (user.getPassword().trim().length() != 0) 
			&& (user.getCategory().trim().length() != 0) && (user.getType().trim().length() != 0)){
			return true;
		} else {
			return false;
		}
	}
}
