package com.rudra.caption.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "captions")
public class Caption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String captionText;

    //  NEW FIELDS
    private String imageName;
    private String mode;

    private LocalDateTime createdAt;

    //  GETTERS & SETTERS

    public int getId() {
        return id;
    }

    public String getCaptionText() {
        return captionText;
    }

    public void setCaptionText(String captionText) {
        this.captionText = captionText;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}