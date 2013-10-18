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
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER", catalog = "Enterprise")
public class User implements java.io.Serializable {

	// Fields

	private Integer userid;
	private Registrant registrant;
	private String email;
	private String password;
	private String name;
	private Integer isdeleted;
	private Set<Userrole> userroles = new HashSet<Userrole>(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String email, String password, String name, Integer isdeleted) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public User(Registrant registrant, String email, String password,
			String name, Integer isdeleted, Set<Userrole> userroles) {
		this.registrant = registrant;
		this.email = email;
		this.password = password;
		this.name = name;
		this.isdeleted = isdeleted;
		this.userroles = userroles;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "USERID", unique = true, nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRANTID")
	public Registrant getRegistrant() {
		return this.registrant;
	}

	public void setRegistrant(Registrant registrant) {
		this.registrant = registrant;
	}

	@Column(name = "EMAIL", nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PASSWORD", nullable = false, length = 45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Userrole> getUserroles() {
		return this.userroles;
	}

	public void setUserroles(Set<Userrole> userroles) {
		this.userroles = userroles;
	}

}