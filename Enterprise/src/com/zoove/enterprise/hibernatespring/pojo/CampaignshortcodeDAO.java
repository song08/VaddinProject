package com.zoove.enterprise.hibernatespring.pojo;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Campaignshortcode entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zoove.enterprise.hibernatespring.pojo.Campaignshortcode
 * @author MyEclipse Persistence Tools
 */

public class CampaignshortcodeDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(CampaignshortcodeDAO.class);
	// property constants
	public static final String SHORTCODE = "shortcode";

	protected void initDao() {
		// do nothing
	}

	public void save(Campaignshortcode transientInstance) {
		log.debug("saving Campaignshortcode instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Campaignshortcode persistentInstance) {
		log.debug("deleting Campaignshortcode instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Campaignshortcode findById(java.lang.Integer id) {
		log.debug("getting Campaignshortcode instance with id: " + id);
		try {
			Campaignshortcode instance = (Campaignshortcode) getHibernateTemplate()
					.get("com.zoove.enterprise.hibernatespring.pojo.Campaignshortcode",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Campaignshortcode> findByExample(Campaignshortcode instance) {
		log.debug("finding Campaignshortcode instance by example");
		try {
			List<Campaignshortcode> results = (List<Campaignshortcode>) getHibernateTemplate()
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
		log.debug("finding Campaignshortcode instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Campaignshortcode as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Campaignshortcode> findByShortcode(Object shortcode) {
		return findByProperty(SHORTCODE, shortcode);
	}

	public List findAll() {
		log.debug("finding all Campaignshortcode instances");
		try {
			String queryString = "from Campaignshortcode";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Campaignshortcode merge(Campaignshortcode detachedInstance) {
		log.debug("merging Campaignshortcode instance");
		try {
			Campaignshortcode result = (Campaignshortcode) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Campaignshortcode instance) {
		log.debug("attaching dirty Campaignshortcode instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Campaignshortcode instance) {
		log.debug("attaching clean Campaignshortcode instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CampaignshortcodeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CampaignshortcodeDAO) ctx.getBean("CampaignshortcodeDAO");
	}
}