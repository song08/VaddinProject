package com.zoove.enterprise.hibernatespring.pojo;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * CountyNielson entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.CountyNielson
 * @author MyEclipse Persistence Tools
 */

public class CountyNielsonDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(CountyNielsonDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(CountyNielson transientInstance) {
		log.debug("saving CountyNielson instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CountyNielson persistentInstance) {
		log.debug("deleting CountyNielson instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CountyNielson findById(
			com.zoove.enterprise.hibernatespring.pojo.CountyNielsonId id) {
		log.debug("getting CountyNielson instance with id: " + id);
		try {
			CountyNielson instance = (CountyNielson) getHibernateTemplate()
					.get("com.zoove.enterprise.hibernatespring.pojo.CountyNielson",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<CountyNielson> findByExample(CountyNielson instance) {
		log.debug("finding CountyNielson instance by example");
		try {
			List<CountyNielson> results = (List<CountyNielson>) getHibernateTemplate()
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
		log.debug("finding CountyNielson instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CountyNielson as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all CountyNielson instances");
		try {
			String queryString = "from CountyNielson";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CountyNielson merge(CountyNielson detachedInstance) {
		log.debug("merging CountyNielson instance");
		try {
			CountyNielson result = (CountyNielson) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CountyNielson instance) {
		log.debug("attaching dirty CountyNielson instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CountyNielson instance) {
		log.debug("attaching clean CountyNielson instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CountyNielsonDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CountyNielsonDAO) ctx.getBean("CountyNielsonDAO");
	}
}