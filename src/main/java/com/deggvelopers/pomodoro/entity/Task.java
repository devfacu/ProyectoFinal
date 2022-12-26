package com.deggvelopers.pomodoro.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Task implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String name;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat( pattern="dd-MM-yyyy")
	private Date date;
	@JoinColumn(referencedColumnName = "id")
	@ManyToOne
	private Project project;
	@Enumerated(EnumType.STRING)
	private Priority priority;
	private Integer investedTime;
	private Boolean done;
	private Integer pomQuantity;
	private Integer pomFinalized;
	private Integer pomDuration;

	public Task() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Integer getInvestedTime() {
		return investedTime;
	}

	public void setInvestedTime(Integer investedTime) {
		this.investedTime = investedTime;
	}

	public Boolean isDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public Integer getPomQuantity() {
		return pomQuantity;
	}

	public void setPomQuantity(Integer pomQuantity) {
		this.pomQuantity = pomQuantity;
	}

	public Integer getPomDuration() {
		return pomDuration;
	}

	public void setPomDuration(Integer pomDuration) {
		this.pomDuration = pomDuration;
	}

	public Integer getPomFinalized() {
		return pomFinalized;
	}

	public void setPomFinalized(Integer pomFinalized) {
		this.pomFinalized = pomFinalized;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
