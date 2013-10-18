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
 * Prefix entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PREFIX", catalog = "Enterprise")
public class Prefix implements java.io.Serializable {

	// Fields

	private Integer prefixid;
	private String prefix;
	private Integer isdeleted;
	private Set<Registry> registries = new HashSet<Registry>(0);
	private Set<Numberpool> numberpools = new HashSet<Numberpool>(0);

	// Constructors

	/** default constructor */
	public Prefix() {
	}

	/** minimal constructor */
	public Prefix(String prefix, Integer isdeleted) {
		this.prefix = prefix;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Prefix(String prefix, Integer isdeleted, Set<Registry> registries,
			Set<Numberpool> numberpools) {
		this.prefix = prefix;
		this.isdeleted = isdeleted;
		this.registries = registries;
		this.numberpools = numberpools;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "PREFIXID", unique = true, nullable = false)
	public Integer getPrefixid() {
		return this.prefixid;
	}

	public void setPrefixid(Integer prefixid) {
		this.prefixid = prefixid;
	}

	@Column(name = "PREFIX", nullable = false, length = 16)
	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prefix")
	public Set<Registry> getRegistries() {
		return this.registries;
	}

	public void setRegistries(Set<Registry> registries) {
		this.registries = registries;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prefix")
	public Set<Numberpool> getNumberpools() {
		return this.numberpools;
	}

	public void setNumberpools(Set<Numberpool> numberpools) {
		this.numberpools = numberpools;
	}

}