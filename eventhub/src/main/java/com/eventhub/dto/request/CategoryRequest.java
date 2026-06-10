package com.eventhub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Pattern(
            regexp = "^[A-Za-z& ]+$",
            message = "Category name should contain only letters"
    )
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    // Added icon field to the request payload
    @NotBlank(message = "Icon identifier is required")
    private String icon;

    public CategoryRequest() {
    }

    // Updated constructor
    public CategoryRequest(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
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

    // Getter and Setter for Icon
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}