package org.iesalixar.servidor.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="jobs")
public class Job implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Lob
	@Column(name="description", nullable=false)
	private String description;
		
	@Column(name="price", nullable=false)
	private int price;
	
	@ManyToOne
	private Category categoryJ;
	
	@Column(name="location", nullable=false)
	private String location;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private User user;
	
	@Column(name="finished", columnDefinition="BOOLEAN")
	private boolean finished = false;
	
	@Column(name="myFavorites", columnDefinition="BOOLEAN")
	private boolean favorites = false;
	
	@Column(nullable=false,columnDefinition="BOOLEAN")	
	private boolean requested = false;
		
	@Column(name="jobDate")
	private LocalDateTime jobDate;
	
	private byte[] file;
			
	
	public Job() {
		// TODO Auto-generated constructor stub
	}

	public Job(String title, String description, int price, Category categoryJ) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
		this.categoryJ = categoryJ;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Category getCategoryJ() {
		return categoryJ;
	}

	public void setCategoryJ(Category categoryJ) {
		this.categoryJ = categoryJ;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public boolean isFavorites() {
		return favorites;
	}

	public void setFavorites(boolean favorites) {
		this.favorites = favorites;
	}

	public boolean isRequested() {
		return requested;
	}

	public void setRequested(boolean requested) {
		this.requested = requested;
	}

	public LocalDateTime getJobDate() {
		return jobDate;
	}

	public void setJobDate(LocalDateTime jobDate) {
		this.jobDate = jobDate;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	
	
	
	
}
