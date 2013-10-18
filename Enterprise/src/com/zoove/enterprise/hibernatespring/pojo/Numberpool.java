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
 * Numberpool entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NUMBERPOOL", catalog = "Enterprise")
public class Numberpool implements java.io.Serializable {

	// Fields

	private Integer numberpoolid;
	private Registrar registrar;
	private Prefix prefix;
	private Carrier carrier;
	private Integer isdeleted;

	// Constructors

	/** default constructor */
	public Numberpool() {
	}

	/** full constructor */
	public Numberpool(Registrar registrar, Prefix prefix, Carrier carrier,
			Integer isdeleted) {
		this.registrar = registrar;
		this.prefix = prefix;
		this.carrier = carrier;
		this.isdeleted = isdeleted;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "NUMBERPOOLID", unique = true, nullable = false)
	public Integer getNumberpoolid() {
		return this.numberpoolid;
	}

	public void setNumberpoolid(Integer numberpoolid) {
		this.numberpoolid = numberpoolid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRARID", nullable = false)
	public Registrar getRegistrar() {
		return this.registrar;
	}

	public void setRegistrar(Registrar registrar) {
		this.registrar = registrar;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PREFIXID", nullable = false)
	public Prefix getPrefix() {
		return this.prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARRIERID", nullable = false)
	public Carrier getCarrier() {
		return this.carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

}