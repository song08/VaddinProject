package com.zoove.enterprise.hibernatespring.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Campaign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CAMPAIGN", catalog = "Enterprise")
public class Campaign implements java.io.Serializable {

	// Fields

	private Integer campaignid;
	private Registry registry;
	private String name;
	private String description;
	private Integer isdeleted;
	private Set<Campaignschedule> campaignschedules = new HashSet<Campaignschedule>(
			0);

	// Constructors

	/** default constructor */
	public Campaign() {
	}

	/** minimal constructor */
	public Campaign(String name, Integer isdeleted) {
		this.name = name;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Campaign(Registry registry, String name, String description,
			Integer isdeleted, Set<Campaignschedule> campaignschedules) {
		this.registry = registry;
		this.name = name;
		this.description = description;
		this.isdeleted = isdeleted;
		this.campaignschedules = campaignschedules;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "CAMPAIGNID", unique = true, nullable = false)
	public Integer getCampaignid() {
		return this.campaignid;
	}

	public void setCampaignid(Integer campaignid) {
		this.campaignid = campaignid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRYID")
	public Registry getRegistry() {
		return this.registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	@Column(name = "NAME", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION")
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "campaign")
	public Set<Campaignschedule> getCampaignschedules() {
		return this.campaignschedules;
	}

	public void setCampaignschedules(Set<Campaignschedule> campaignschedules) {
		this.campaignschedules = campaignschedules;
	}

}