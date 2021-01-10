package com.example.devmark.model;

/**
 * Models a post
 */
public class Post {
    private String id;
    private String project_id;
    private String project_title;
    private String description;
    private String otherRequirements;
    private String location;
    private String requirements;
    private String creator;

    public Post(){

    }

    public Post(String id, String project_id, String project_title,
                String description,
                String otherRequirements,
                String location,
                String requirements,
                String creator) {
        this.id = id;
        this.project_id = project_id;
        this.project_title = project_title;
        this.description = description;
        this.otherRequirements = otherRequirements;
        this.location = location;
        this.requirements = requirements;
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setOtherRequirements(String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
}
