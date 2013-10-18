package com.zoove.enterprise.ui;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

public class Footer extends VerticalLayout {
	
	public Footer() {
		this.setHeight("48px");
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.setSizeUndefined();
		Label copyright = new Label("&#169; 2012 Zoove Corporation");
		copyright.setContentMode(Label.CONTENT_XHTML);
		hl.addComponent(copyright);
		
		hl.addComponent(new Link("About", new ExternalResource("")));
		hl.addComponent(new Link("Privacy", new ExternalResource("")));
		hl.addComponent(new Link("Contact Us", new ExternalResource("")));
		
		this.addComponent(hl);
		this.setComponentAlignment(hl, Alignment.TOP_RIGHT);
	}
}
