package com.example.devmark.model;

/**
 * Models a request
 */
public class AppliedPosts {
    private String request_id;
    private String requester;
    private String creator;
    private String project_title;
    private String description;

    public AppliedPosts(){

    }

    public AppliedPosts(String request_id, String requester, String creator, String project_title, String description){
        this.request_id = request_id;
        this.requester = requester;
        this.creator = creator;
        this.project_title = project_title;
        this.description = description;
    }


    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
