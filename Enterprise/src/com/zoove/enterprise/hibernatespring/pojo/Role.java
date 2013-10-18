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
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROLE", catalog = "Enterprise")
public class Role implements java.io.Serializable {

	// Fields

	private Integer roleid;
	private String role;
	private String description;
	private Integer isdeleted;
	private Set<Userrole> userroles = new HashSet<Userrole>(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String role, Integer isdeleted) {
		this.role = role;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Role(String role, String description, Integer isdeleted,
			Set<Userrole> userroles) {
		this.role = role;
		this.description = description;
		this.isdeleted = isdeleted;
		this.userroles = userroles;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ROLEID", unique = true, nullable = false)
	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	@Column(name = "ROLE", nullable = false, length = 45)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "DESCRIPTION", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<Userrole> getUserroles() {
		return this.userroles;
	}

	public void setUserroles(Set<Userrole> userroles) {
		this.userroles = userroles;
	}

}