package com.zoove.enterprise.ui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;

import sun.misc.BASE64Encoder;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.zoove.enterprise.SessionData;
import com.zoove.enterprise.hibernatespring.HibernateUtils;
import com.zoove.enterprise.hibernatespring.pojo.User;
import com.zoove.enterprise.hibernatespring.pojo.UserDAO;

public class LoginForm extends VerticalLayout implements ClickListener {
	Logger logger = Logger.getLogger(LoginForm.class);
	
	private final TextField email = new TextField();
	private final PasswordField password = new PasswordField ();
	
	public LoginForm () {
		this.setSizeUndefined();
		this.setStyleName("myform");

		this.addComponent(new Embedded("",new ThemeResource("images/logo.png")));
		this.addComponent(email);
		email.setWidth("200px");
		email.setInputPrompt("Email");
		this.addComponent(password);
		password.setWidth("200px");
		password.setInputPrompt("Password");
		
		Button btn= new Button ("Login", this);
		btn.setIcon(new ThemeResource("icons/16/ok.png"));
		this.addComponent(btn);
				
		email.focus();
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		this.submit();
	}
	
	protected void submit() {
		Notification notification = new Notification("", Notification.TYPE_WARNING_MESSAGE);
		notification.setPosition(Notification.POSITION_CENTERED_TOP);
		notification.setHtmlContentAllowed(true);
		//notification.setIcon(new ThemeResource("icons/16/error.png"));
		if (!(new EmailValidator("").isValid(email.getValue()))) {
			notification.setDescription("<b><font color=red>Invalid email address</font></b>");
			getWindow().showNotification(notification);
			return;
		}
		
		BeanFactory beanFactory = HibernateUtils.getBeanFactory();
		if (beanFactory==null) {
			notification.setDescription("<b><font color=red>System unavailable</font></b>");
			getWindow().showNotification(notification);
			return;
		}
		
		UserDAO userDAO = (UserDAO)beanFactory.getBean("UserDAO");
		List<User> users = userDAO.findByEmail(email.getValue());

		if (users.isEmpty()) {
			notification.setDescription("<b><font color=red>Invalid email/password</font></b>");
			getWindow().showNotification(notification);
			return;
		}

		User user = null;
		for (Iterator<User> it=users.iterator(); it.hasNext(); ) {
			User u = it.next();
			if (u.getIsdeleted()!=1) {
				user = u;
				break;
			}
		}
		
		if (user==null) {
			notification.setDescription("<b><font color=red>Invalid email/password</font></b>");
			getWindow().showNotification(notification);
			return;
		}
		
		String passwordDigest = (String)password.getValue();
		try {
			passwordDigest = new BASE64Encoder().encodeBuffer(MessageDigest.getInstance("MD5").digest(passwordDigest.getBytes())).replace("\n", "");
		}
		catch (NoSuchAlgorithmException e) {
			logger.error("Cannot get MD5 instance:"+e.getMessage());
			notification.setDescription("<b><font color=red>System unavailable</font></b>");
			getWindow().showNotification(notification);
			return;
		}
		
		if (user.getPassword().equals(passwordDigest)) {
			Map<String, Object> userData = SessionData.getUserData();
			userData.put("USER", user);
			Main main = new Main();
			getWindow().setContent(main);
		}
		else {
			notification.setDescription("<b><font color=red>Invalid email/password</font></b>");
			getWindow().showNotification(notification);
		}
	}
}
