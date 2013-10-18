package com.zoove.enterprise;

import com.vaadin.Application;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class EnterpriseApplication extends Application {
	
	@Override
	public void init() {
		SessionData sessionData = new SessionData(this);
		this.getContext().addTransactionListener(sessionData);
		
		Window mainWindow = new Window("Enterprise Application");
		this.setTheme("zoove");
		setMainWindow(mainWindow);
		CssLayout body = new CssLayout() {
			protected String getCss(Component c) {
				return "background: #0291D3; margin-left: auto; margin-right: auto";
			}
		};
		body.setSizeFull();
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		com.zoove.enterprise.ui.LoginForm loginForm=new com.zoove.enterprise.ui.LoginForm();
		vl.addComponent(loginForm);
		body.addComponent(vl);
		vl.setComponentAlignment(loginForm, Alignment.TOP_CENTER);
		mainWindow.setContent(body);
	}

}
