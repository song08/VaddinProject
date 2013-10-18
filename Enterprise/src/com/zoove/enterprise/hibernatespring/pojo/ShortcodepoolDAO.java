package com.zoove.enterprise.hibernatespring.pojo;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Shortcodepool entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Shortcodepool
 * @author MyEclipse Persistence Tools
 */

public class ShortcodepoolDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(ShortcodepoolDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String CARRIERID = "carrierid";
	public static final String STARTSC = "startsc";
	public static final String ENDSC = "endsc";
	public static final String CAPABILITY = "capability";

	protected void initDao() {
		// do nothing
	}

	public void save(Shortcodepool transientInstance) {
		log.debug("saving Shortcodepool instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Shortcodepool persistentInstance) {
		log.debug("deleting Shortcodepool instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Shortcodepool findById(java.lang.Integer id) {
		log.debug("getting Shortcodepool instance with id: " + id);
		try {
			Shortcodepool instance = (Shortcodepool) getHibernateTemplate()
					.get("com.zoove.enterprise.hibernatespring.pojo.Shortcodepool",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Shortcodepool> findByExample(Shortcodepool instance) {
		log.debug("finding Shortcodepool instance by example");
		try {
			List<Shortcodepool> results = (List<Shortcodepool>) getHibernateTemplate()
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
		log.debug("finding Shortcodepool instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Shortcodepool as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Shortcodepool> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Shortcodepool> findByCarrierid(Object carrierid) {
		return findByProperty(CARRIERID, carrierid);
	}

	public List<Shortcodepool> findByStartsc(Object startsc) {
		return findByProperty(STARTSC, startsc);
	}

	public List<Shortcodepool> findByEndsc(Object endsc) {
		return findByProperty(ENDSC, endsc);
	}

	public List<Shortcodepool> findByCapability(Object capability) {
		return findByProperty(CAPABILITY, capability);
	}

	public List findAll() {
		log.debug("finding all Shortcodepool instances");
		try {
			String queryString = "from Shortcodepool";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Shortcodepool merge(Shortcodepool detachedInstance) {
		log.debug("merging Shortcodepool instance");
		try {
			Shortcodepool result = (Shortcodepool) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Shortcodepool instance) {
		log.debug("attaching dirty Shortcodepool instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Shortcodepool instance) {
		log.debug("attaching clean Shortcodepool instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ShortcodepoolDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ShortcodepoolDAO) ctx.getBean("ShortcodepoolDAO");
	}
}