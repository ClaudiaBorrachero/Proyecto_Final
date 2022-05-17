package org.iesalixar.servidor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category implements Serializable{

//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;	
	
	@Id
	@Column(name="name", unique=true, nullable=false)
	private String name;
	
	private String icono;
	
	@OneToMany(mappedBy="categoryJ", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Job> jobs = new ArrayList<Job>();
	
	public Category() {
		// TODO Auto-generated constructor stub
	}

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
	
	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	
	//HELPERS ADD JOBS TO CATEGORY
//	public void addJob(Job job) {
//		this.jobs.add(job);
//		job.setCategoryJ(this);
//	}
//	
//	public void removeJob(Job job) {
//		this.jobs.remove(job);
//		job.setCategoryJ(null);
//	}
	
}
