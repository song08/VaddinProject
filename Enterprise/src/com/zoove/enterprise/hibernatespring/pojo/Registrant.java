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
 * Registrant entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "REGISTRANT", catalog = "Enterprise")
public class Registrant implements java.io.Serializable {

	// Fields

	private Integer registrantid;
	private Registrar registrar;
	private Contact contact;
	private Address address;
	private String fullname;
	private Integer isdeleted;
	private Set<User> users = new HashSet<User>(0);
	private Set<Registry> registries = new HashSet<Registry>(0);

	// Constructors

	/** default constructor */
	public Registrant() {
	}

	/** minimal constructor */
	public Registrant(Registrar registrar, Contact contact, Address address,
			String fullname, Integer isdeleted) {
		this.registrar = registrar;
		this.contact = contact;
		this.address = address;
		this.fullname = fullname;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Registrant(Registrar registrar, Contact contact, Address address,
			String fullname, Integer isdeleted, Set<User> users,
			Set<Registry> registries) {
		this.registrar = registrar;
		this.contact = contact;
		this.address = address;
		this.fullname = fullname;
		this.isdeleted = isdeleted;
		this.users = users;
		this.registries = registries;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "REGISTRANTID", unique = true, nullable = false)
	public Integer getRegistrantid() {
		return this.registrantid;
	}

	public void setRegistrantid(Integer registrantid) {
		this.registrantid = registrantid;
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
	@JoinColumn(name = "CONTACTID", nullable = false)
	public Contact getContact() {
		return this.contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESSID", nullable = false)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registrant")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registrant")
	public Set<Registry> getRegistries() {
		return this.registries;
	}

	public void setRegistries(Set<Registry> registries) {
		this.registries = registries;
	}

}