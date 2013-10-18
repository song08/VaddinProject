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
 * Registrar entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Registrar
 * @author MyEclipse Persistence Tools
 */

public class RegistrarDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(RegistrarDAO.class);
	// property constants
	public static final String FULLNAME = "fullname";

	protected void initDao() {
		// do nothing
	}

	public void save(Registrar transientInstance) {
		log.debug("saving Registrar instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Registrar persistentInstance) {
		log.debug("deleting Registrar instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Registrar findById(java.lang.Integer id) {
		log.debug("getting Registrar instance with id: " + id);
		try {
			Registrar instance = (Registrar) getHibernateTemplate().get(
					"com.zoove.enterprise.hibernatespring.pojo.Registrar", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Registrar> findByExample(Registrar instance) {
		log.debug("finding Registrar instance by example");
		try {
			List<Registrar> results = (List<Registrar>) getHibernateTemplate()
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
		log.debug("finding Registrar instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Registrar as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Registrar> findByFullname(Object fullname) {
		return findByProperty(FULLNAME, fullname);
	}

	public List findAll() {
		log.debug("finding all Registrar instances");
		try {
			String queryString = "from Registrar";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Registrar merge(Registrar detachedInstance) {
		log.debug("merging Registrar instance");
		try {
			Registrar result = (Registrar) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Registrar instance) {
		log.debug("attaching dirty Registrar instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Registrar instance) {
		log.debug("attaching clean Registrar instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RegistrarDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RegistrarDAO) ctx.getBean("RegistrarDAO");
	}
}