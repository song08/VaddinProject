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
 * Contact entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CONTACT", catalog = "Enterprise")
public class Contact implements java.io.Serializable {

	// Fields

	private Integer contactid;
	private Address address;
	private String contactname;
	private String emailaddress;
	private String website;
	private String workphone;
	private String mobile;
	private String fax;
	private Integer isdeleted;
	private Set<Registrar> registrars = new HashSet<Registrar>(0);
	private Set<Registrant> registrants = new HashSet<Registrant>(0);

	// Constructors

	/** default constructor */
	public Contact() {
	}

	/** minimal constructor */
	public Contact(String contactname, Integer isdeleted) {
		this.contactname = contactname;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Contact(Address address, String contactname, String emailaddress,
			String website, String workphone, String mobile, String fax,
			Integer isdeleted, Set<Registrar> registrars,
			Set<Registrant> registrants) {
		this.address = address;
		this.contactname = contactname;
		this.emailaddress = emailaddress;
		this.website = website;
		this.workphone = workphone;
		this.mobile = mobile;
		this.fax = fax;
		this.isdeleted = isdeleted;
		this.registrars = registrars;
		this.registrants = registrants;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "CONTACTID", unique = true, nullable = false)
	public Integer getContactid() {
		return this.contactid;
	}

	public void setContactid(Integer contactid) {
		this.contactid = contactid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESSID")
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Column(name = "CONTACTNAME", nullable = false, length = 45)
	public String getContactname() {
		return this.contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	@Column(name = "EMAILADDRESS")
	public String getEmailaddress() {
		return this.emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	@Column(name = "WEBSITE")
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "WORKPHONE", length = 45)
	public String getWorkphone() {
		return this.workphone;
	}

	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	@Column(name = "MOBILE", length = 45)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "FAX", length = 45)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contact")
	public Set<Registrar> getRegistrars() {
		return this.registrars;
	}

	public void setRegistrars(Set<Registrar> registrars) {
		this.registrars = registrars;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contact")
	public Set<Registrant> getRegistrants() {
		return this.registrants;
	}

	public void setRegistrants(Set<Registrant> registrants) {
		this.registrants = registrants;
	}

}