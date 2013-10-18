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
 * Locationlevel entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Locationlevel
 * @author MyEclipse Persistence Tools
 */

public class LocationlevelDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(LocationlevelDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(Locationlevel transientInstance) {
		log.debug("saving Locationlevel instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Locationlevel persistentInstance) {
		log.debug("deleting Locationlevel instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Locationlevel findById(java.lang.String id) {
		log.debug("getting Locationlevel instance with id: " + id);
		try {
			Locationlevel instance = (Locationlevel) getHibernateTemplate()
					.get("com.zoove.enterprise.hibernatespring.pojo.Locationlevel",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Locationlevel> findByExample(Locationlevel instance) {
		log.debug("finding Locationlevel instance by example");
		try {
			List<Locationlevel> results = (List<Locationlevel>) getHibernateTemplate()
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
		log.debug("finding Locationlevel instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Locationlevel as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Locationlevel instances");
		try {
			String queryString = "from Locationlevel";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Locationlevel merge(Locationlevel detachedInstance) {
		log.debug("merging Locationlevel instance");
		try {
			Locationlevel result = (Locationlevel) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Locationlevel instance) {
		log.debug("attaching dirty Locationlevel instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Locationlevel instance) {
		log.debug("attaching clean Locationlevel instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static LocationlevelDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (LocationlevelDAO) ctx.getBean("LocationlevelDAO");
	}
}