package toolkit;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;

public class MailBox {

	private Session session;
	private String host, port, username, password;

	public MailBox(String username, String password) {
		this.host = "smtp.office365.com";
		this.port = "587";
		this.username = username;
		this.password = password;
		this.session = getSession(host, port, username, password);
	}

	private Session getSession(String host, String port, String authMailId, String authPassword) {
		Properties props = System.getProperties();

		// Setup mail server
		props.put("mail.debug", "false");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");

		// Get session
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(authMailId, authPassword);
			}
		};
		return Session.getInstance(props, auth);
	}

	public void sendEmail(String recipients, String body) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sender@domain"));
			InternetAddress[] addresses = InternetAddress.parse(recipients);
			message.addRecipients(Message.RecipientType.TO, addresses);
			message.addRecipient(Message.RecipientType.CC, new InternetAddress("reciever@domain"));
			message.setSubject("AutoGen Mail");
			message.setText(body);
			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readMail(String folderName) {

		try {
			Store store = session.getStore("imaps");
			store.connect(host, username, password);

			// Get folder
			Folder folder = store.getFolder(folderName.toUpperCase());
			folder.open(Folder.READ_ONLY);

			// Get directory
			Message messages[] = folder.getMessages();
			ArrayUtils.reverse(messages);
			for (Message message : messages) {
				System.out.println(message.getSentDate() + ": " + message.getFrom() + "\t" + message.getSubject());
			}

			// Close connection
			folder.close(false);
			store.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
