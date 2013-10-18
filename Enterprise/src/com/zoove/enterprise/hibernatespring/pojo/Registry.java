package com.zoove.enterprise.hibernatespring.pojo;

import java.sql.Timestamp;
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
 * Registry entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "REGISTRY", catalog = "Enterprise")
public class Registry implements java.io.Serializable {

	// Fields

	private Integer registryid;
	private Prefix prefix;
	private Registrant registrant;
	private Location location;
	private Carrier carrier;
	private String digits;
	private String literal;
	private Timestamp startdate;
	private Timestamp enddate;
	private Integer isdeleted;
	private Set<Replyword> replywords = new HashSet<Replyword>(0);
	private Set<Campaign> campaigns = new HashSet<Campaign>(0);
	private Set<Campaignshortcode> campaignshortcodes = new HashSet<Campaignshortcode>(
			0);

	// Constructors

	/** default constructor */
	public Registry() {
	}

	/** minimal constructor */
	public Registry(Prefix prefix, Registrant registrant, Location location,
			Carrier carrier, String digits, Timestamp startdate,
			Integer isdeleted) {
		this.prefix = prefix;
		this.registrant = registrant;
		this.location = location;
		this.carrier = carrier;
		this.digits = digits;
		this.startdate = startdate;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Registry(Prefix prefix, Registrant registrant, Location location,
			Carrier carrier, String digits, String literal,
			Timestamp startdate, Timestamp enddate, Integer isdeleted,
			Set<Replyword> replywords, Set<Campaign> campaigns,
			Set<Campaignshortcode> campaignshortcodes) {
		this.prefix = prefix;
		this.registrant = registrant;
		this.location = location;
		this.carrier = carrier;
		this.digits = digits;
		this.literal = literal;
		this.startdate = startdate;
		this.enddate = enddate;
		this.isdeleted = isdeleted;
		this.replywords = replywords;
		this.campaigns = campaigns;
		this.campaignshortcodes = campaignshortcodes;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "REGISTRYID", unique = true, nullable = false)
	public Integer getRegistryid() {
		return this.registryid;
	}

	public void setRegistryid(Integer registryid) {
		this.registryid = registryid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PREFIXID", nullable = false)
	public Prefix getPrefix() {
		return this.prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRANTID", nullable = false)
	public Registrant getRegistrant() {
		return this.registrant;
	}

	public void setRegistrant(Registrant registrant) {
		this.registrant = registrant;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCATIONID", nullable = false)
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARRIERID", nullable = false)
	public Carrier getCarrier() {
		return this.carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	@Column(name = "DIGITS", nullable = false, length = 45)
	public String getDigits() {
		return this.digits;
	}

	public void setDigits(String digits) {
		this.digits = digits;
	}

	@Column(name = "LITERAL", length = 45)
	public String getLiteral() {
		return this.literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registry")
	public Set<Replyword> getReplywords() {
		return this.replywords;
	}

	public void setReplywords(Set<Replyword> replywords) {
		this.replywords = replywords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registry")
	public Set<Campaign> getCampaigns() {
		return this.campaigns;
	}

	public void setCampaigns(Set<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registry")
	public Set<Campaignshortcode> getCampaignshortcodes() {
		return this.campaignshortcodes;
	}

	public void setCampaignshortcodes(Set<Campaignshortcode> campaignshortcodes) {
		this.campaignshortcodes = campaignshortcodes;
	}

}