package com.zoove.enterprise.hibernatespring.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Campaignshortcode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CAMPAIGNSHORTCODE", catalog = "Enterprise")
public class Campaignshortcode implements java.io.Serializable {

	// Fields

	private Integer shortcodeid;
	private Registry registry;
	private Carrier carrier;
	private String shortcode;
	private Integer isdeleted;

	// Constructors

	/** default constructor */
	public Campaignshortcode() {
	}

	/** full constructor */
	public Campaignshortcode(Registry registry, Carrier carrier,
			String shortcode, Integer isdeleted) {
		this.registry = registry;
		this.carrier = carrier;
		this.shortcode = shortcode;
		this.isdeleted = isdeleted;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "SHORTCODEID", unique = true, nullable = false)
	public Integer getShortcodeid() {
		return this.shortcodeid;
	}

	public void setShortcodeid(Integer shortcodeid) {
		this.shortcodeid = shortcodeid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAMPAIGNID", nullable = false)
	public Registry getRegistry() {
		return this.registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARRIERID", nullable = false)
	public Carrier getCarrier() {
		return this.carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	@Column(name = "SHORTCODE", nullable = false, length = 32)
	public String getShortcode() {
		return this.shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

}