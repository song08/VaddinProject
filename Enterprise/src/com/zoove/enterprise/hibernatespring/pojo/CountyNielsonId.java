package com.zoove.enterprise.hibernatespring.pojo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CountyNielsonId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class CountyNielsonId implements java.io.Serializable {

	// Fields

	private Integer nelsonId;
	private String county;
	private String state;

	// Constructors

	/** default constructor */
	public CountyNielsonId() {
	}

	/** full constructor */
	public CountyNielsonId(Integer nelsonId, String county, String state) {
		this.nelsonId = nelsonId;
		this.county = county;
		this.state = state;
	}

	// Property accessors

	@Column(name = "NelsonId")
	public Integer getNelsonId() {
		return this.nelsonId;
	}

	public void setNelsonId(Integer nelsonId) {
		this.nelsonId = nelsonId;
	}

	@Column(name = "County", length = 128)
	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	@Column(name = "State", length = 2)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CountyNielsonId))
			return false;
		CountyNielsonId castOther = (CountyNielsonId) other;

		return ((this.getNelsonId() == castOther.getNelsonId()) || (this
				.getNelsonId() != null && castOther.getNelsonId() != null && this
				.getNelsonId().equals(castOther.getNelsonId())))
				&& ((this.getCounty() == castOther.getCounty()) || (this
						.getCounty() != null && castOther.getCounty() != null && this
						.getCounty().equals(castOther.getCounty())))
				&& ((this.getState() == castOther.getState()) || (this
						.getState() != null && castOther.getState() != null && this
						.getState().equals(castOther.getState())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getNelsonId() == null ? 0 : this.getNelsonId().hashCode());
		result = 37 * result
				+ (getCounty() == null ? 0 : this.getCounty().hashCode());
		result = 37 * result
				+ (getState() == null ? 0 : this.getState().hashCode());
		return result;
	}

}