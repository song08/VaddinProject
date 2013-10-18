package com.zoove.enterprise.hibernatespring;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.vaadin.data.hbnutil.HbnContainer.SessionManager;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateUtils {
	private final static Logger logger = Logger.getLogger(HibernateUtils.class);
	private static BeanFactory beanFactory = null;
	private static SessionFactory sessionFactory = null;
	private static SessionManager sessionManager = null;

	static {
		try {
			logger.debug("Initializing HibernateUtil");
			//beanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
			beanFactory = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
			
			sessionFactory = (SessionFactory)beanFactory.getBean("sessionFactory");
			
			sessionManager = new HbnSessionManager();
		}
		catch (Exception ex) {
			logger.error("Could not init Spring BeanFactory and/or could not get Hibernate sessionFactory");
		}
	}
	
	public static BeanFactory getBeanFactory() {
		try {
			if (beanFactory==null)
				beanFactory = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
			return beanFactory;
		}
		catch (Exception ex) {
			logger.error("Could not init Spring BeanFactory object");
			return null;
		}
	}
	
	public static SessionFactory getSessionFactory() {
		if (!sessionFactory.getCurrentSession().getTransaction().isActive())
			sessionFactory.getCurrentSession().beginTransaction();
		
		return sessionFactory;
	}
	
	public static SessionManager getSessionManager() {
		return sessionManager;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
