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
import javax.persistence.UniqueConstraint;

/**
 * Location entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "LOCATION", catalog = "Enterprise", uniqueConstraints = @UniqueConstraint(columnNames = {
		"LOCATIONPARENTID", "LOCATIONNAME" }))
public class Location implements java.io.Serializable {

	// Fields

	private Integer locationid;
	private String locationname;
	private String locationlevel;
	private Integer locationparentid;
	private Integer hierarchyid;
	private Integer isdeleted;
	private Set<Locationarea> locationareas = new HashSet<Locationarea>(0);
	private Set<Content> contents = new HashSet<Content>(0);
	private Set<Registry> registries = new HashSet<Registry>(0);

	// Constructors

	/** default constructor */
	public Location() {
	}

	/** minimal constructor */
	public Location(String locationname, String locationlevel, Integer isdeleted) {
		this.locationname = locationname;
		this.locationlevel = locationlevel;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Location(String locationname, String locationlevel,
			Integer locationparentid, Integer hierarchyid, Integer isdeleted,
			Set<Locationarea> locationareas, Set<Content> contents,
			Set<Registry> registries) {
		this.locationname = locationname;
		this.locationlevel = locationlevel;
		this.locationparentid = locationparentid;
		this.hierarchyid = hierarchyid;
		this.isdeleted = isdeleted;
		this.locationareas = locationareas;
		this.contents = contents;
		this.registries = registries;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "LOCATIONID", unique = true, nullable = false)
	public Integer getLocationid() {
		return this.locationid;
	}

	public void setLocationid(Integer locationid) {
		this.locationid = locationid;
	}

	@Column(name = "LOCATIONNAME", nullable = false)
	public String getLocationname() {
		return this.locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	@Column(name = "LOCATIONLEVEL", nullable = false, length = 45)
	public String getLocationlevel() {
		return this.locationlevel;
	}

	public void setLocationlevel(String locationlevel) {
		this.locationlevel = locationlevel;
	}

	@Column(name = "LOCATIONPARENTID")
	public Integer getLocationparentid() {
		return this.locationparentid;
	}

	public void setLocationparentid(Integer locationparentid) {
		this.locationparentid = locationparentid;
	}

	@Column(name = "HIERARCHYID")
	public Integer getHierarchyid() {
		return this.hierarchyid;
	}

	public void setHierarchyid(Integer hierarchyid) {
		this.hierarchyid = hierarchyid;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "location")
	public Set<Locationarea> getLocationareas() {
		return this.locationareas;
	}

	public void setLocationareas(Set<Locationarea> locationareas) {
		this.locationareas = locationareas;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "location")
	public Set<Content> getContents() {
		return this.contents;
	}

	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "location")
	public Set<Registry> getRegistries() {
		return this.registries;
	}

	public void setRegistries(Set<Registry> registries) {
		this.registries = registries;
	}

}