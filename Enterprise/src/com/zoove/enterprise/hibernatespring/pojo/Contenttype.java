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
 * Contenttype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CONTENTTYPE", catalog = "Enterprise")
public class Contenttype implements java.io.Serializable {

	// Fields

	private Integer contenttypeid;
	private String name;
	private Integer isdeleted;
	private Set<Content> contents = new HashSet<Content>(0);

	// Constructors

	/** default constructor */
	public Contenttype() {
	}

	/** minimal constructor */
	public Contenttype(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Contenttype(String name, Integer isdeleted, Set<Content> contents) {
		this.name = name;
		this.isdeleted = isdeleted;
		this.contents = contents;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "CONTENTTYPEID", unique = true, nullable = false)
	public Integer getContenttypeid() {
		return this.contenttypeid;
	}

	public void setContenttypeid(Integer contenttypeid) {
		this.contenttypeid = contenttypeid;
	}

	@Column(name = "NAME", length = 45)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contenttype")
	public Set<Content> getContents() {
		return this.contents;
	}

	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}

}