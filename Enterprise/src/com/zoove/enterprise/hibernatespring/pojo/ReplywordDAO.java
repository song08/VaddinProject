package com.zoove.enterprise.hibernatespring.pojo;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Replyword entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Replyword
 * @author MyEclipse Persistence Tools
 */

public class ReplywordDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(ReplywordDAO.class);
	// property constants
	public static final String REPLYWORD = "replyword";

	protected void initDao() {
		// do nothing
	}

	public void save(Replyword transientInstance) {
		log.debug("saving Replyword instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Replyword persistentInstance) {
		log.debug("deleting Replyword instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Replyword findById(java.lang.Integer id) {
		log.debug("getting Replyword instance with id: " + id);
		try {
			Replyword instance = (Replyword) getHibernateTemplate().get(
					"com.zoove.enterprise.hibernatespring.pojo.Replyword", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Replyword> findByExample(Replyword instance) {
		log.debug("finding Replyword instance by example");
		try {
			List<Replyword> results = (List<Replyword>) getHibernateTemplate()
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
		log.debug("finding Replyword instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Replyword as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Replyword> findByReplyword(Object replyword) {
		return findByProperty(REPLYWORD, replyword);
	}

	public List findAll() {
		log.debug("finding all Replyword instances");
		try {
			String queryString = "from Replyword";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Replyword merge(Replyword detachedInstance) {
		log.debug("merging Replyword instance");
		try {
			Replyword result = (Replyword) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Replyword instance) {
		log.debug("attaching dirty Replyword instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Replyword instance) {
		log.debug("attaching clean Replyword instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ReplywordDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ReplywordDAO) ctx.getBean("ReplywordDAO");
	}
}