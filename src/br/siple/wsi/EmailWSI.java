package br.siple.wsi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailWSI {
	
	public boolean sendMail(String to, String subject, String text) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		final Properties userProps = getProperties();
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					System.out.println("un " + userProps.getProperty("username"));
					System.out.println("pw " + userProps.getProperty("password"));
					return new PasswordAuthentication(userProps.getProperty("username"),userProps.getProperty("password"));
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("camiloperic@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private Properties getProperties() {
		Properties props = new Properties();
	    InputStream inputStream = this.getClass().getClassLoader()
	        .getResourceAsStream("smtp.properties");

	    if (inputStream == null) {
	        System.out.println("File not found");
	    }

	    try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	    return props;
	}
	
}
