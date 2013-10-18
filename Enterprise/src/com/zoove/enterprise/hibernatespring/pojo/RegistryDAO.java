package com.zoove.enterprise.hibernatespring.pojo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Registry entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Registry
 * @author MyEclipse Persistence Tools
 */

public class RegistryDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(RegistryDAO.class);
	// property constants
	public static final String DIGITS = "digits";
	public static final String LITERAL = "literal";

	protected void initDao() {
		// do nothing
	}

	public void save(Registry transientInstance) {
		log.debug("saving Registry instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Registry persistentInstance) {
		log.debug("deleting Registry instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Registry findById(java.lang.Integer id) {
		log.debug("getting Registry instance with id: " + id);
		try {
			Registry instance = (Registry) getHibernateTemplate().get(
					"com.zoove.enterprise.hibernatespring.pojo.Registry", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Registry> findByExample(Registry instance) {
		log.debug("finding Registry instance by example");
		try {
			List<Registry> results = (List<Registry>) getHibernateTemplate()
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
		log.debug("finding Registry instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Registry as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Registry> findByDigits(Object digits) {
		return findByProperty(DIGITS, digits);
	}

	public List<Registry> findByLiteral(Object literal) {
		return findByProperty(LITERAL, literal);
	}

	public List findAll() {
		log.debug("finding all Registry instances");
		try {
			String queryString = "from Registry";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Registry merge(Registry detachedInstance) {
		log.debug("merging Registry instance");
		try {
			Registry result = (Registry) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Registry instance) {
		log.debug("attaching dirty Registry instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Registry instance) {
		log.debug("attaching clean Registry instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RegistryDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RegistryDAO) ctx.getBean("RegistryDAO");
	}
}