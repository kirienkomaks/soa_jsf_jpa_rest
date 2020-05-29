package com.lab8.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "movies")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idMovie", nullable = false)
    private int idMovie;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "uri", nullable = false)
    private String uri;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="idUser")

    @JsonBackReference
    private User user;

    public Movie(String title, String url, User user) {
        this.title=title;
        this.uri=url;
        this.user=user;
    }

    public Movie() {super();}

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
