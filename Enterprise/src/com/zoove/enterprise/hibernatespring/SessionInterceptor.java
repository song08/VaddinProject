package com.zoove.enterprise.hibernatespring;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;

@WebFilter(urlPatterns = { "/*" }, dispatcherTypes = { DispatcherType.REQUEST,
		DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR })
public class SessionInterceptor implements Filter {
	private static final Logger logger = Logger
			.getLogger(SessionInterceptor.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("Interceptor init");
	}

	@Override
	public void destroy() {
		logger.info("Interceptor destroy");
		final Session session = HibernateUtils.getSessionFactory()
				.getCurrentSession();

		if (session.getTransaction().isActive())
			session.getTransaction().commit();

		if (session.isOpen())
			session.close();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		logger.debug("doFilter starts");
		final Session session = HibernateUtils.getSessionFactory()
				.getCurrentSession();

		try {
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			chain.doFilter(request, response);

			if (session.getTransaction().isActive())
				session.getTransaction().commit();
		} catch (StaleObjectStateException e) {
			logger.error(e);

			if (session.getTransaction().isActive())
				session.getTransaction().rollback();

			throw e;
		} catch (Throwable e) {
			logger.error(e);

			if (session.getTransaction().isActive())
				session.getTransaction().rollback();

			throw new ServletException(e);
		}
		logger.debug("doFilter ends");
	}
}
