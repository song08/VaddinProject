package com.zoove.enterprise.hibernatespring.pojo;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Content entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Content
 * @author MyEclipse Persistence Tools
 */

public class ContentDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(ContentDAO.class);
	// property constants
	public static final String SUBJECT = "subject";
	public static final String MSGBODY = "msgbody";
	public static final String URLS = "urls";
	public static final String ENCODING = "encoding";
	public static final String DELAY = "delay";
	public static final String RADIUS = "radius";
	public static final String GROUPNAME = "groupname";

	protected void initDao() {
		// do nothing
	}

	public void save(Content transientInstance) {
		log.debug("saving Content instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Content persistentInstance) {
		log.debug("deleting Content instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Content findById(java.lang.Integer id) {
		log.debug("getting Content instance with id: " + id);
		try {
			Content instance = (Content) getHibernateTemplate().get(
					"com.zoove.enterprise.hibernatespring.pojo.Content", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Content> findByExample(Content instance) {
		log.debug("finding Content instance by example");
		try {
			List<Content> results = (List<Content>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Content instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Content as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Content> findBySubject(Object subject) {
		return findByProperty(SUBJECT, subject);
	}

	public List<Content> findByMsgbody(Object msgbody) {
		return findByProperty(MSGBODY, msgbody);
	}

	public List<Content> findByUrls(Object urls) {
		return findByProperty(URLS, urls);
	}

	public List<Content> findByEncoding(Object encoding) {
		return findByProperty(ENCODING, encoding);
	}

	public List<Content> findByDelay(Object delay) {
		return findByProperty(DELAY, delay);
	}

	public List<Content> findByRadius(Object radius) {
		return findByProperty(RADIUS, radius);
	}

	public List<Content> findByGroupname(Object groupname) {
		return findByProperty(GROUPNAME, groupname);
	}

	public List findAll() {
		log.debug("finding all Content instances");
		try {
			String queryString = "from Content";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Content merge(Content detachedInstance) {
		log.debug("merging Content instance");
		try {
			Content result = (Content) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Content instance) {
		log.debug("attaching dirty Content instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Content instance) {
		log.debug("attaching clean Content instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ContentDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ContentDAO) ctx.getBean("ContentDAO");
	}
}