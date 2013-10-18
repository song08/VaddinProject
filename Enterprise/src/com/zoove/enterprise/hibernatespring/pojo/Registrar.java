package com.zoove.enterprise.hibernatespring.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Registrar entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "REGISTRAR", catalog = "Enterprise")
public class Registrar implements java.io.Serializable {

	// Fields

	private Integer registrarid;
	private Contact contact;
	private Address address;
	private String fullname;
	private Integer isdeleted;
	private Set<Registrant> registrants = new HashSet<Registrant>(0);
	private Set<Numberpool> numberpools = new HashSet<Numberpool>(0);

	// Constructors

	/** default constructor */
	public Registrar() {
	}

	/** minimal constructor */
	public Registrar(String fullname, Integer isdeleted) {
		this.fullname = fullname;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Registrar(Contact contact, Address address, String fullname,
			Integer isdeleted, Set<Registrant> registrants,
			Set<Numberpool> numberpools) {
		this.contact = contact;
		this.address = address;
		this.fullname = fullname;
		this.isdeleted = isdeleted;
		this.registrants = registrants;
		this.numberpools = numberpools;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "REGISTRARID", unique = true, nullable = false)
	public Integer getRegistrarid() {
		return this.registrarid;
	}

	public void setRegistrarid(Integer registrarid) {
		this.registrarid = registrarid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTACTID")
	public Contact getContact() {
		return this.contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESSID")
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Column(name = "FULLNAME", nullable = false)
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registrar")
	public Set<Registrant> getRegistrants() {
		return this.registrants;
	}

	public void setRegistrants(Set<Registrant> registrants) {
		this.registrants = registrants;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registrar")
	public Set<Numberpool> getNumberpools() {
		return this.numberpools;
	}

	public void setNumberpools(Set<Numberpool> numberpools) {
		this.numberpools = numberpools;
	}

}