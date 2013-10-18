package com.zoove.enterprise.hibernatespring.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Locationlevel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "LOCATIONLEVEL", catalog = "Enterprise")
public class Locationlevel implements java.io.Serializable {

	// Fields

	private Integer levelid;
	private String levelname;
	private Integer isdeleted;

	// Constructors

	/** default constructor */
	public Locationlevel() {
	}

	/** full constructor */
	public Locationlevel(String levelname, Integer isdeleted) {
		this.levelname = levelname;
		this.isdeleted = isdeleted;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "LEVELID", unique = true, nullable = false)
	public Integer getLevelid() {
		return this.levelid;
	}

	public void setLevelid(Integer levelid) {
		this.levelid = levelid;
	}

	@Column(name = "LEVELNAME", nullable = false, length = 45)
	public String getLevelname() {
		return this.levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

}