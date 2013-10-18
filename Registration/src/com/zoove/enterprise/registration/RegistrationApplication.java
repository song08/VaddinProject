package com.zoove.enterprise.registration;


import com.vaadin.Application;
import com.vaadin.ui.*;
import com.zoove.enterprise.registration.ui.LocationMapView;

public class RegistrationApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Registration Application");
		
		LocationMapView mapView = new LocationMapView();
		
		mainWindow.setContent(mapView);
		setMainWindow(mainWindow);
	}

}
