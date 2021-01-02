package com.example.devmark.model;

import java.util.List;

public interface DialogListener {

    void applyTextContent(String projectTitle, String description, List<String> requirements, String country);

}
