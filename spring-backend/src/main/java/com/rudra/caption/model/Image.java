package com.rudra.caption.model;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imagePath;
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
