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
 * Userrole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USERROLE", catalog = "Enterprise")
public class Userrole implements java.io.Serializable {

	// Fields

	private Integer userroleid;
	private User user;
	private Role role;
	private Integer isdeleted;

	// Constructors

	/** default constructor */
	public Userrole() {
	}

	/** full constructor */
	public Userrole(User user, Role role, Integer isdeleted) {
		this.user = user;
		this.role = role;
		this.isdeleted = isdeleted;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "USERROLEID", unique = true, nullable = false)
	public Integer getUserroleid() {
		return this.userroleid;
	}

	public void setUserroleid(Integer userroleid) {
		this.userroleid = userroleid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERID", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLEID", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

}