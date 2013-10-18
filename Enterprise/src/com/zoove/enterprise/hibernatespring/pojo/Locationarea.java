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
 * Locationarea entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "LOCATIONAREA", catalog = "Enterprise")
public class Locationarea implements java.io.Serializable {

	// Fields

	private Integer areaid;
	private Location location;
	private String areaname;
	private String description;
	private String geometryjson;
	private Integer nielsonid;
	private Integer isdeleted;

	// Constructors

	/** default constructor */
	public Locationarea() {
	}

	/** minimal constructor */
	public Locationarea(Location location, String areaname,
			String geometryjson, Integer isdeleted) {
		this.location = location;
		this.areaname = areaname;
		this.geometryjson = geometryjson;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Locationarea(Location location, String areaname, String description,
			String geometryjson, Integer nielsonid, Integer isdeleted) {
		this.location = location;
		this.areaname = areaname;
		this.description = description;
		this.geometryjson = geometryjson;
		this.nielsonid = nielsonid;
		this.isdeleted = isdeleted;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "AREAID", unique = true, nullable = false)
	public Integer getAreaid() {
		return this.areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCATIONID", nullable = false)
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Column(name = "AREANAME", nullable = false, length = 128)
	public String getAreaname() {
		return this.areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	@Column(name = "DESCRIPTION", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "GEOMETRYJSON", nullable = false, length = 16777215)
	public String getGeometryjson() {
		return this.geometryjson;
	}

	public void setGeometryjson(String geometryjson) {
		this.geometryjson = geometryjson;
	}

	@Column(name = "NIELSONID")
	public Integer getNielsonid() {
		return this.nielsonid;
	}

	public void setNielsonid(Integer nielsonid) {
		this.nielsonid = nielsonid;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

}