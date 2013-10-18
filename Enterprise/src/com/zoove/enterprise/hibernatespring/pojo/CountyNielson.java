package com.zoove.enterprise.hibernatespring.pojo;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CountyNielson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "COUNTY_NIELSON", catalog = "Enterprise")
public class CountyNielson implements java.io.Serializable {

	// Fields

	private CountyNielsonId id;

	// Constructors

	/** default constructor */
	public CountyNielson() {
	}

	/** full constructor */
	public CountyNielson(CountyNielsonId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "nelsonId", column = @Column(name = "NelsonId")),
			@AttributeOverride(name = "county", column = @Column(name = "County", length = 128)),
			@AttributeOverride(name = "state", column = @Column(name = "State", length = 2)) })
	public CountyNielsonId getId() {
		return this.id;
	}

	public void setId(CountyNielsonId id) {
		this.id = id;
	}

}