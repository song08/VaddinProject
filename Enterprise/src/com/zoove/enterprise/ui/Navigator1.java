package com.zoove.enterprise.ui;

import java.util.Map;
import java.util.Set;

import org.vaadin.hene.popupbutton.PopupButton;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.zoove.enterprise.SessionData;
import com.zoove.enterprise.hibernatespring.pojo.User;
import com.zoove.enterprise.hibernatespring.pojo.Userrole;
import com.zoove.enterprise.ui.carrier.CarrierView;
import com.zoove.enterprise.ui.carrier.shortcode.ShortcodeView;
import com.zoove.enterprise.ui.registrar.RegistrarView;
import com.zoove.enterprise.ui.user.UserView;

public class Navigator1 extends VerticalLayout {
	Main main;
	
	public Navigator1(Main main) {
		this.main = main;
		this.init();
	}
	
	void init() {
		ThemeResource blankPng = new ThemeResource("icons/16/blank.png");
		
		Map<String, Object> userData = SessionData.getUserData();
		
		User user = (User)userData.get("USER");
		
		if (user==null)
			getApplication().close();
		
		Set<Userrole> roles = user.getUserroles();
		
		HorizontalLayout outhl = new HorizontalLayout();
		outhl.setWidth("100%");
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeUndefined();
		
		Embedded logo=new Embedded(null,new ThemeResource("images/logo.png"));
		hl.addComponent(logo);
		hl.setComponentAlignment(logo, Alignment.BOTTOM_LEFT);
		
		MenuBar menubar = new MenuBar();
		menubar.setAutoOpen(true);
		menubar.setHtmlContentAllowed(true);
		final MenuBar.MenuItem dashboard = menubar.addItem("<u>Dashboard</u>", null, null);
		final MenuBar.MenuItem settings = menubar.addItem("<u>Settings</u>", new ThemeResource("icons/16/settings.png"), null);
		settings.addItem("Registrar", blankPng, new Command() {
			public void menuSelected(MenuItem selectedItem) {
				RegistrarView registrarView = new RegistrarView();
				main.getBody().removeAllComponents();
				main.getBody().addComponent(registrarView);
			}
		});
		final MenuBar.MenuItem carrier = settings.addItem("Carrier", blankPng, new Command() {
			public void menuSelected(MenuItem selectedItem) {
				VerticalLayout body = main.getBody();
				CarrierView carrierView = new CarrierView();
				body.removeAllComponents();
				body.addComponent(carrierView);
			}
		});
		carrier.addItem("Shortcode Pool", blankPng, new Command() {
			public void menuSelected(MenuItem selectedItem) {
				ShortcodeView shortcodeView = new ShortcodeView();
				main.getBody().removeAllComponents();
				main.getBody().addComponent(shortcodeView);
			}
		});
		
		settings.addItem("User", new ThemeResource("icons/16/users.png"), new Command() {
			public void menuSelected(MenuItem selectedItem) {
				VerticalLayout body = main.getBody();
				UserView userView = new UserView();
				body.removeAllComponents();
				body.addComponent(userView);
			}
		});

		hl.addComponent(menubar);
		hl.setComponentAlignment(menubar, Alignment.BOTTOM_LEFT);
		hl.setSpacing(true);
		
		outhl.addComponent(hl);
		
		PopupButton currentUser = new PopupButton (user.getName());
		currentUser.setIcon(new ThemeResource("icons/16/user.png"));
		VerticalLayout userPopupLayout = new VerticalLayout ();
		userPopupLayout.setSizeUndefined();
		currentUser.setComponent(userPopupLayout);
		
		Button profile = new Button ("My Profile");
		profile.setStyleName(BaseTheme.BUTTON_LINK);
		profile.setIcon(blankPng);
		userPopupLayout.addComponent(profile);
		
		Button logout = new Button ("Logout");
		logout.setStyleName(BaseTheme.BUTTON_LINK);
		logout.setIcon(new ThemeResource("icons/16/logout.png"));
		logout.addListener(new ClickListener () {
			@Override
			public void buttonClick(ClickEvent event) {
				event.getComponent().getApplication().close();
			}
		});
		
		userPopupLayout.addComponent(logout);
		
		outhl.addComponent(currentUser);
		outhl.setComponentAlignment(currentUser, Alignment.BOTTOM_RIGHT);
		
		this.addComponent(outhl);
	}
}
