package com.zoove.enterprise.hibernatespring.pojo;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Campaignschedule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CAMPAIGNSCHEDULE", catalog = "Enterprise")
public class Campaignschedule implements java.io.Serializable {

	// Fields

	private Integer scheduleid;
	private Campaign campaign;
	private Timestamp startdate;
	private Timestamp enddate;
	private Integer isdeleted;

	// Constructors

	/** default constructor */
	public Campaignschedule() {
	}

	/** minimal constructor */
	public Campaignschedule(Campaign campaign, Timestamp startdate,
			Integer isdeleted) {
		this.campaign = campaign;
		this.startdate = startdate;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Campaignschedule(Campaign campaign, Timestamp startdate,
			Timestamp enddate, Integer isdeleted) {
		this.campaign = campaign;
		this.startdate = startdate;
		this.enddate = enddate;
		this.isdeleted = isdeleted;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "SCHEDULEID", unique = true, nullable = false)
	public Integer getScheduleid() {
		return this.scheduleid;
	}

	public void setScheduleid(Integer scheduleid) {
		this.scheduleid = scheduleid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAMPAIGNID", nullable = false)
	public Campaign getCampaign() {
		return this.campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	@Column(name = "STARTDATE", nullable = false, length = 19)
	public Timestamp getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	@Column(name = "ENDDATE", length = 19)
	public Timestamp getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

}