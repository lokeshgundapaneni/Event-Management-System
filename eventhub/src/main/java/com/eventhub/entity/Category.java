package com.eventhub.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	
	@NotBlank(message = "Category name is required")
	@Pattern(
	        regexp = "^[A-Za-z& ]+$",
	        message = "Category name should contain only letters, spaces, and ampersands"
	)
	private String name;
	
	@NotBlank(message="Description is required")
	private String description;

	@NotBlank(message="Icon identifier is required")
	private String icon;

	@JsonManagedReference("category-event")
	@OneToMany(mappedBy = "category")
	private List<Event> events;

	// FIX: Updated the constructor parameter's @Pattern constraint to match the field level configuration
	public Category(Long id,
			@NotBlank(message = "Category name is required") 
			@Pattern(regexp = "^[A-Za-z& ]+$", message="Category name should contain only letters, spaces, and ampersands") String name,
			@NotBlank(message = "Description is required") String description, 
			@NotBlank(message = "Icon identifier is required") String icon, 
			List<Event> events) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.icon = icon;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
				", icon=" + icon +
				"]";
	}
}