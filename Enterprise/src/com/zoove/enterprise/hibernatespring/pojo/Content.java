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
 * Content entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CONTENT", catalog = "Enterprise")
public class Content implements java.io.Serializable {

	// Fields

	private Integer contentid;
	private Contenttype contenttype;
	private Location location;
	private Carrier carrier;
	private String subject;
	private String msgbody;
	private String urls;
	private String encoding;
	private Integer delay;
	private Double radius;
	private String groupname;
	private Integer isdeleted;

	// Constructors

	/** default constructor */
	public Content() {
	}

	/** minimal constructor */
	public Content(Contenttype contenttype, Location location, Carrier carrier,
			Double radius, String groupname, Integer isdeleted) {
		this.contenttype = contenttype;
		this.location = location;
		this.carrier = carrier;
		this.radius = radius;
		this.groupname = groupname;
		this.isdeleted = isdeleted;
	}

	/** full constructor */
	public Content(Contenttype contenttype, Location location, Carrier carrier,
			String subject, String msgbody, String urls, String encoding,
			Integer delay, Double radius, String groupname, Integer isdeleted) {
		this.contenttype = contenttype;
		this.location = location;
		this.carrier = carrier;
		this.subject = subject;
		this.msgbody = msgbody;
		this.urls = urls;
		this.encoding = encoding;
		this.delay = delay;
		this.radius = radius;
		this.groupname = groupname;
		this.isdeleted = isdeleted;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "CONTENTID", unique = true, nullable = false)
	public Integer getContentid() {
		return this.contentid;
	}

	public void setContentid(Integer contentid) {
		this.contentid = contentid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENTTYPEID", nullable = false)
	public Contenttype getContenttype() {
		return this.contenttype;
	}

	public void setContenttype(Contenttype contenttype) {
		this.contenttype = contenttype;
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

	@Column(name = "SUBJECT", length = 120)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "MSGBODY")
	public String getMsgbody() {
		return this.msgbody;
	}

	public void setMsgbody(String msgbody) {
		this.msgbody = msgbody;
	}

	@Column(name = "URLS")
	public String getUrls() {
		return this.urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	@Column(name = "ENCODING", length = 20)
	public String getEncoding() {
		return this.encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Column(name = "DELAY")
	public Integer getDelay() {
		return this.delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	@Column(name = "RADIUS", nullable = false, precision = 10)
	public Double getRadius() {
		return this.radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	@Column(name = "GROUPNAME", nullable = false, length = 32)
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

}