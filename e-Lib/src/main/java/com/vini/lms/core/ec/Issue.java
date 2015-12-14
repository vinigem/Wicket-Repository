package com.vini.lms.core.ec;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="LMS_ISSUE")
public class Issue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="issueId",length=6)
	private Long id;
	
	@Column(name="userId",length=6)
	private Long userId;
	
	@Column(name="bookId",length=6)
	private Long bookId;
	
	@Column(name="issueDate")
	@Temporal(TemporalType.DATE)
	private Date issueDate;
	
	@Column(name="expReturnDate")
	@Temporal(TemporalType.DATE)
	private Date expReturnDate;
	
	@Column(name="actReturnDate")
	@Temporal(TemporalType.DATE)
	private Date actReturnDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
		Calendar cal = Calendar.getInstance();  
		cal.setTime(issueDate);
		cal.add(Calendar.MONTH, 1);
		this.expReturnDate=cal.getTime();
	}

	public Date getExpReturnDate() {
		return expReturnDate;
	}

	public void setExpReturnDate(Date expReturnDate) {
		this.expReturnDate = expReturnDate;
	}

	public Date getActReturnDate() {
		return actReturnDate;
	}

	public void setActReturnDate(Date actReturnDate) {
		this.actReturnDate = actReturnDate;
	}

	
	

}
