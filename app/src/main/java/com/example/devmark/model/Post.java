package com.example.devmark.model;

public class Post {
    private String uuid;
    private String project_title, description, otherRequirements, location;
    private String requirements;
    private String creator;

    public Post(){

    }

    public Post(String uuid, String project_title,
                String description,
                String otherRequirements,
                String location,
                String requirements,
                String creator) {
        this.uuid = uuid;
        this.project_title = project_title;
        this.description = description;
        this.otherRequirements = otherRequirements;
        this.location = location;
        this.requirements = requirements;
        this.creator = creator;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}
