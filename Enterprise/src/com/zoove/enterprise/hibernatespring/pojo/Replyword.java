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
 * Replyword entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "REPLYWORD", catalog = "Enterprise")
public class Replyword implements java.io.Serializable {

	// Fields

	private Integer replywordid;
	private Registry registry;
	private String replyword;
	private Integer isdeleted;

	// Constructors

	/** default constructor */
	public Replyword() {
	}

	/** full constructor */
	public Replyword(Registry registry, String replyword, Integer isdeleted) {
		this.registry = registry;
		this.replyword = replyword;
		this.isdeleted = isdeleted;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "REPLYWORDID", unique = true, nullable = false)
	public Integer getReplywordid() {
		return this.replywordid;
	}

	public void setReplywordid(Integer replywordid) {
		this.replywordid = replywordid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAMPAIGNID", nullable = false)
	public Registry getRegistry() {
		return this.registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	@Column(name = "REPLYWORD", nullable = false)
	public String getReplyword() {
		return this.replyword;
	}

	public void setReplyword(String replyword) {
		this.replyword = replyword;
	}

	@Column(name = "ISDELETED", nullable = false)
	public Integer getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}

}