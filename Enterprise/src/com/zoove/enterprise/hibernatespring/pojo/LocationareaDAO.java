package com.zoove.enterprise.hibernatespring.pojo;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Locationarea entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Locationarea
 * @author MyEclipse Persistence Tools
 */

public class LocationareaDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(LocationareaDAO.class);
	// property constants
	public static final String AREANAME = "areaname";
	public static final String DESCRIPTION = "description";
	public static final String GEOMETRYJSON = "geometryjson";
	public static final String NIELSONID = "nielsonid";

	protected void initDao() {
		// do nothing
	}

	public void save(Locationarea transientInstance) {
		log.debug("saving Locationarea instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Locationarea persistentInstance) {
		log.debug("deleting Locationarea instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Locationarea findById(java.lang.Integer id) {
		log.debug("getting Locationarea instance with id: " + id);
		try {
			Locationarea instance = (Locationarea) getHibernateTemplate().get(
					"com.zoove.enterprise.hibernatespring.pojo.Locationarea",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Locationarea> findByExample(Locationarea instance) {
		log.debug("finding Locationarea instance by example");
		try {
			List<Locationarea> results = (List<Locationarea>) getHibernateTemplate()
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
		log.debug("finding Locationarea instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Locationarea as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Locationarea> findByAreaname(Object areaname) {
		return findByProperty(AREANAME, areaname);
	}

	public List<Locationarea> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List<Locationarea> findByGeometryjson(Object geometryjson) {
		return findByProperty(GEOMETRYJSON, geometryjson);
	}

	public List<Locationarea> findByNielsonid(Object nielsonid) {
		return findByProperty(NIELSONID, nielsonid);
	}

	public List findAll() {
		log.debug("finding all Locationarea instances");
		try {
			String queryString = "from Locationarea";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Locationarea merge(Locationarea detachedInstance) {
		log.debug("merging Locationarea instance");
		try {
			Locationarea result = (Locationarea) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Locationarea instance) {
		log.debug("attaching dirty Locationarea instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Locationarea instance) {
		log.debug("attaching clean Locationarea instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static LocationareaDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (LocationareaDAO) ctx.getBean("LocationareaDAO");
	}
}