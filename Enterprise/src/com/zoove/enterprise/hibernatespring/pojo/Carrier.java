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
 * Carrier entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CARRIER", catalog = "Enterprise")
public class Carrier implements java.io.Serializable {

	// Fields

	private Integer carrierid;
	private String carriername;
	private String description;
	private String carrierlevel;
	private Integer isdeleted;
	private Set<Shortcodepool> shortcodepools = new HashSet<Shortcodepool>(0);
	private Set<Content> contents = new HashSet<Content>(0);
	private Set<Registry> registries = new HashSet<Registry>(0);
	private Set<Campaignshortcode> campaignshortcodes = new HashSet<Campaignshortcode>(
			0);
	private Set<Numberpool> numberpools = new HashSet<Numberpool>(0);

	// Constructors

	/** default constructor */
	public Carrier() {
	}

	/** minimal constructor */
	public Carrier(String carriername, String carrierlevel, Integer isdeleted) {
		this.carriername = carriername;
		this.carrierlevel = carrierlevel;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Carrier(String carriername, String description, String carrierlevel,
			Integer isdeleted, Set<Shortcodepool> shortcodepools,
			Set<Content> contents, Set<Registry> registries,
			Set<Campaignshortcode> campaignshortcodes,
			Set<Numberpool> numberpools) {
		this.carriername = carriername;
		this.description = description;
		this.carrierlevel = carrierlevel;
		this.isdeleted = isdeleted;
		this.shortcodepools = shortcodepools;
		this.contents = contents;
		this.registries = registries;
		this.campaignshortcodes = campaignshortcodes;
		this.numberpools = numberpools;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "CARRIERID", unique = true, nullable = false)
	public Integer getCarrierid() {
		return this.carrierid;
	}

	public void setCarrierid(Integer carrierid) {
		this.carrierid = carrierid;
	}

	@Column(name = "CARRIERNAME", nullable = false, length = 45)
	public String getCarriername() {
		return this.carriername;
	}

	public void setCarriername(String carriername) {
		this.carriername = carriername;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "CARRIERLEVEL", nullable = false, length = 45)
	public String getCarrierlevel() {
		return this.carrierlevel;
	}

	public void setCarrierlevel(String carrierlevel) {
		this.carrierlevel = carrierlevel;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrier")
	public Set<Shortcodepool> getShortcodepools() {
		return this.shortcodepools;
	}

	public void setShortcodepools(Set<Shortcodepool> shortcodepools) {
		this.shortcodepools = shortcodepools;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrier")
	public Set<Content> getContents() {
		return this.contents;
	}

	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrier")
	public Set<Registry> getRegistries() {
		return this.registries;
	}

	public void setRegistries(Set<Registry> registries) {
		this.registries = registries;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrier")
	public Set<Campaignshortcode> getCampaignshortcodes() {
		return this.campaignshortcodes;
	}

	public void setCampaignshortcodes(Set<Campaignshortcode> campaignshortcodes) {
		this.campaignshortcodes = campaignshortcodes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carrier")
	public Set<Numberpool> getNumberpools() {
		return this.numberpools;
	}

	public void setNumberpools(Set<Numberpool> numberpools) {
		this.numberpools = numberpools;
	}

}