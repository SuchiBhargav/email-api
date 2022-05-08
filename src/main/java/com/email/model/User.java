package com.email.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Table
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;
    private String name;
    @Column(unique=true)
    private String email;
    private String pass;
    private String role;

    public User(String name, String email, String pass, String role) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.role = role;
    }
}
