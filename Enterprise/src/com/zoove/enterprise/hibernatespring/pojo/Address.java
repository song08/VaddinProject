package com.zoove.enterprise.hibernatespring.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Address entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ADDRESS", catalog = "Enterprise")
public class Address implements java.io.Serializable {

	// Fields

	private Integer addressid;
	private String line1;
	private String line2;
	private String line3;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private Integer isdeleted;
	private Set<Contact> contacts = new HashSet<Contact>(0);
	private Set<Registrant> registrants = new HashSet<Registrant>(0);
	private Set<Registrar> registrars = new HashSet<Registrar>(0);

	// Constructors

	/** default constructor */
	public Address() {
	}

	/** minimal constructor */
	public Address(String city, String state, String country, Integer isdeleted) {
		this.city = city;
		this.state = state;
		this.country = country;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Address(String line1, String line2, String line3, String city,
			String state, String country, String zipcode, Integer isdeleted,
			Set<Contact> contacts, Set<Registrant> registrants,
			Set<Registrar> registrars) {
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
		this.isdeleted = isdeleted;
		this.contacts = contacts;
		this.registrants = registrants;
		this.registrars = registrars;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ADDRESSID", unique = true, nullable = false)
	public Integer getAddressid() {
		return this.addressid;
	}

	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}

	@Column(name = "LINE_1")
	public String getLine1() {
		return this.line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	@Column(name = "LINE_2")
	public String getLine2() {
		return this.line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	@Column(name = "LINE_3")
	public String getLine3() {
		return this.line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	@Column(name = "CITY", nullable = false)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATE", nullable = false, length = 64)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "COUNTRY", nullable = false, length = 64)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "ZIPCODE", length = 45)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "address")
	public Set<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "address")
	public Set<Registrant> getRegistrants() {
		return this.registrants;
	}

	public void setRegistrants(Set<Registrant> registrants) {
		this.registrants = registrants;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "address")
	public Set<Registrar> getRegistrars() {
		return this.registrars;
	}

	public void setRegistrars(Set<Registrar> registrars) {
		this.registrars = registrars;
	}

}