package com.zoove.enterprise.hibernatespring;

import org.hibernate.Session;

import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.data.hbnutil.HbnContainer.SessionManager;

public class HbnSessionManager implements HbnContainer.SessionManager {
	public HbnSessionManager() {
		super();
	}
	
	public Session getSession() {
		Session currentSession = HibernateUtils.getSessionFactory().getCurrentSession();
		if (!currentSession.getTransaction().isActive())
			currentSession.beginTransaction();
		return currentSession;
	}
}
