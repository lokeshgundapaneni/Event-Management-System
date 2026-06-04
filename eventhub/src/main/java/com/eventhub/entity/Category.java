package com.eventhub.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Category name is required")
	@Column(nullable=false,unique=true)
	@Pattern(regexp="^[A-Za-z ]+$")
	private String name;
	
	@NotBlank(message="Description is required")
	private String description;

	@OneToMany(mappedBy = "category")
    private List<Event> events;

	public Category(Long id,
			@NotBlank(message = "Category name is required") @Pattern(regexp = "^[A-Za-Z ]+$") String name,
			@NotBlank(message = "Description is required") String description, List<Event> events) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.events = events;
	}

	public Category() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	@Override
	public String toString() {
	    return "Category [id=" + id +
	            ", name=" + name +
	            ", description=" + description +
	            "]";
	}
	
	
	
	
	
	
	
	
	
	

}
