package com.zoove.enterprise.hibernatespring.pojo;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Userrole entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Userrole
 * @author MyEclipse Persistence Tools
 */

public class UserroleDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(UserroleDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(Userrole transientInstance) {
		log.debug("saving Userrole instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Userrole persistentInstance) {
		log.debug("deleting Userrole instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Userrole findById(
			Integer id) {
		log.debug("getting Userrole instance with id: " + id);
		try {
			Userrole instance = (Userrole) getHibernateTemplate().get(
					"com.zoove.enterprise.hibernatespring.pojo.Userrole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Userrole> findByExample(Userrole instance) {
		log.debug("finding Userrole instance by example");
		try {
			List<Userrole> results = (List<Userrole>) getHibernateTemplate()
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
		log.debug("finding Userrole instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Userrole as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Userrole instances");
		try {
			String queryString = "from Userrole";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Userrole merge(Userrole detachedInstance) {
		log.debug("merging Userrole instance");
		try {
			Userrole result = (Userrole) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Userrole instance) {
		log.debug("attaching dirty Userrole instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Userrole instance) {
		log.debug("attaching clean Userrole instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserroleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserroleDAO) ctx.getBean("UserroleDAO");
	}
}