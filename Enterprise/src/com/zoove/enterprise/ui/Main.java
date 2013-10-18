package com.zoove.enterprise.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

public class Main extends VerticalLayout {
	VerticalLayout body = new VerticalLayout();
	
	public Main() {
		this.setSizeFull();
		
		Navigator navigator = new Navigator(this);
		navigator.setWidth("90%");
		navigator.setStyleName("navigator");
		this.addComponent(navigator);
		this.setComponentAlignment(navigator, Alignment.TOP_CENTER);
		
		body.setStyleName("mainbody");
		body.setWidth("90%");
		body.setHeight("100%");
		this.addComponent(body);
		this.setComponentAlignment(body, Alignment.TOP_CENTER);
		this.setExpandRatio(body, 1.0f);

		Footer footer = new Footer();
		footer.setStyleName("footer");
		footer.setWidth("90%");
		this.addComponent(footer);
		this.setComponentAlignment(footer, Alignment.TOP_CENTER);
		
	}

	public VerticalLayout getBody() {
		return body;
	}

	public void setBody(VerticalLayout body) {
		this.body = body;
	}
	
}
