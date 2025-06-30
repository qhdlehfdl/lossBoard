package com.example.demo.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User {
    @Id
    private String id;

    private String password;
    private String nickname;
    private String name;
    private Integer studentID;

    public User(String id, String password, String nickname, String name, Integer studentID) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.studentID = studentID;
    }
}
