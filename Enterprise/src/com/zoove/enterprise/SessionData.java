package com.zoove.enterprise;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;

public class SessionData implements TransactionListener, Serializable {
	private Map<String, Object> userData;
	private Application sessionApp;
	private static ThreadLocal<SessionData> sessionDataInstance = new ThreadLocal<SessionData>();
	
	public SessionData(Application app) {
		this.sessionApp = app;
		sessionDataInstance.set(this);
		userData = new HashMap<String, Object> ();
	}
	
	@Override
	public void transactionStart(Application application, Object transactionData) {
		if (this.sessionApp == application)
			sessionDataInstance.set(this);
	}

	@Override
	public void transactionEnd(Application application, Object transactionData) {
		if (this.sessionApp == application)
			sessionDataInstance.set(null);
	}

	public static Map<String, Object> getUserData() {
		return sessionDataInstance.get().userData;
	}

	public void setUserData(Map<String, Object> userData) {
		sessionDataInstance.get().userData = userData;
	}

	
}
