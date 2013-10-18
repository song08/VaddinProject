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
 * Registrant entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Registrant
 * @author MyEclipse Persistence Tools
 */

public class RegistrantDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(RegistrantDAO.class);
	// property constants
	public static final String FULLNAME = "fullname";

	protected void initDao() {
		// do nothing
	}

	public void save(Registrant transientInstance) {
		log.debug("saving Registrant instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Registrant persistentInstance) {
		log.debug("deleting Registrant instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Registrant findById(java.lang.Integer id) {
		log.debug("getting Registrant instance with id: " + id);
		try {
			Registrant instance = (Registrant) getHibernateTemplate().get(
					"com.zoove.enterprise.hibernatespring.pojo.Registrant", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Registrant> findByExample(Registrant instance) {
		log.debug("finding Registrant instance by example");
		try {
			List<Registrant> results = (List<Registrant>) getHibernateTemplate()
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
		log.debug("finding Registrant instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Registrant as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Registrant> findByFullname(Object fullname) {
		return findByProperty(FULLNAME, fullname);
	}

	public List findAll() {
		log.debug("finding all Registrant instances");
		try {
			String queryString = "from Registrant";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Registrant merge(Registrant detachedInstance) {
		log.debug("merging Registrant instance");
		try {
			Registrant result = (Registrant) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Registrant instance) {
		log.debug("attaching dirty Registrant instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Registrant instance) {
		log.debug("attaching clean Registrant instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RegistrantDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RegistrantDAO) ctx.getBean("RegistrantDAO");
	}
}