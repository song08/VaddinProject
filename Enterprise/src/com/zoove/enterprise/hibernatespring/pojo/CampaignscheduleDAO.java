package com.zoove.enterprise.hibernatespring.pojo;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Campaignschedule entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Campaignschedule
 * @author MyEclipse Persistence Tools
 */

public class CampaignscheduleDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(CampaignscheduleDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(Campaignschedule transientInstance) {
		log.debug("saving Campaignschedule instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Campaignschedule persistentInstance) {
		log.debug("deleting Campaignschedule instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Campaignschedule findById(java.lang.Integer id) {
		log.debug("getting Campaignschedule instance with id: " + id);
		try {
			Campaignschedule instance = (Campaignschedule) getHibernateTemplate()
					.get("com.zoove.enterprise.hibernatespring.pojo.Campaignschedule",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Campaignschedule> findByExample(Campaignschedule instance) {
		log.debug("finding Campaignschedule instance by example");
		try {
			List<Campaignschedule> results = (List<Campaignschedule>) getHibernateTemplate()
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
		log.debug("finding Campaignschedule instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Campaignschedule as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Campaignschedule instances");
		try {
			String queryString = "from Campaignschedule";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Campaignschedule merge(Campaignschedule detachedInstance) {
		log.debug("merging Campaignschedule instance");
		try {
			Campaignschedule result = (Campaignschedule) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Campaignschedule instance) {
		log.debug("attaching dirty Campaignschedule instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Campaignschedule instance) {
		log.debug("attaching clean Campaignschedule instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CampaignscheduleDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CampaignscheduleDAO) ctx.getBean("CampaignscheduleDAO");
	}
}