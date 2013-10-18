package com.zoove.enterprise.hibernatespring.pojo;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Prefix entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Prefix
 * @author MyEclipse Persistence Tools
 */

public class PrefixDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(PrefixDAO.class);
	// property constants
	public static final String PREFIX = "prefix";

	protected void initDao() {
		// do nothing
	}

	public void save(Prefix transientInstance) {
		log.debug("saving Prefix instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Prefix persistentInstance) {
		log.debug("deleting Prefix instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Prefix findById(java.lang.Integer id) {
		log.debug("getting Prefix instance with id: " + id);
		try {
			Prefix instance = (Prefix) getHibernateTemplate().get(
					"com.zoove.enterprise.hibernatespring.pojo.Prefix", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Prefix> findByExample(Prefix instance) {
		log.debug("finding Prefix instance by example");
		try {
			List<Prefix> results = (List<Prefix>) getHibernateTemplate()
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
		log.debug("finding Prefix instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Prefix as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Prefix> findByPrefix(Object prefix) {
		return findByProperty(PREFIX, prefix);
	}

	public List findAll() {
		log.debug("finding all Prefix instances");
		try {
			String queryString = "from Prefix";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Prefix merge(Prefix detachedInstance) {
		log.debug("merging Prefix instance");
		try {
			Prefix result = (Prefix) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Prefix instance) {
		log.debug("attaching dirty Prefix instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Prefix instance) {
		log.debug("attaching clean Prefix instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PrefixDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PrefixDAO) ctx.getBean("PrefixDAO");
	}
}