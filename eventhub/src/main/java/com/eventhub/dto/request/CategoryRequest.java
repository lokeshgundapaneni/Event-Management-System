package com.eventhub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Category name should contain only letters"
    )
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    public CategoryRequest() {
    }

    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
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
}