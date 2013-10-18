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
 * Shortcodepool entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SHORTCODEPOOL", catalog = "Enterprise")
public class Shortcodepool implements java.io.Serializable {

	// Fields

	private Integer poolid;
	private Carrier carrier;
	private String name;
	private String startsc;
	private String endsc;
	private Integer capability;
	private Integer isdeleted;

	// Constructors

	/** default constructor */
	public Shortcodepool() {
	}

	/** full constructor */
	public Shortcodepool(Carrier carrier, String name, String startsc,
			String endsc, Integer capability, Integer isdeleted) {
		this.carrier = carrier;
		this.name = name;
		this.startsc = startsc;
		this.endsc = endsc;
		this.capability = capability;
		this.isdeleted = isdeleted;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "POOLID", unique = true, nullable = false)
	public Integer getPoolid() {
		return this.poolid;
	}

	public void setPoolid(Integer poolid) {
		this.poolid = poolid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARRIERID", nullable = false)
	public Carrier getCarrier() {
		return this.carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	@Column(name = "NAME", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "STARTSC", nullable = false, length = 32)
	public String getStartsc() {
		return this.startsc;
	}

	public void setStartsc(String startsc) {
		this.startsc = startsc;
	}

	@Column(name = "ENDSC", nullable = false, length = 32)
	public String getEndsc() {
		return this.endsc;
	}

	public void setEndsc(String endsc) {
		this.endsc = endsc;
	}

	@Column(name = "CAPABILITY", nullable = false)
	public Integer getCapability() {
		return this.capability;
	}

	public void setCapability(Integer capability) {
		this.capability = capability;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

}