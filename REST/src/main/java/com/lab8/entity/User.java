package com.lab8.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUser", nullable = false)
    private int idUser;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy="user", cascade=CascadeType.MERGE)
    @JsonManagedReference
    private List<Movie> movies;

    @Lob
    @Column(name="userPicture")
    private byte[] userPicture;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public User() {super();}
    public User(String name, int age, byte[] userPicture) {
        this.name=name;
        this.age=age;
        this.userPicture=userPicture;
    }

    public byte[] getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(byte[] userPicture) {
        this.userPicture = userPicture;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    public byte[] getUserPicture() {
//        return userPicture;
//    }
//
//    public void setUserPicture(byte[] userPicture) {
//        this.userPicture = userPicture;
//    }
}
