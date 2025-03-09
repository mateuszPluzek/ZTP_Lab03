package com.ztp.ztplab03;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Objects;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private Integer idUser;

    @NotBlank(message = "Pole jest wymagane")
    @Size(min = 3, message = "Pole musi mieć więcej niż 3 znaki")
    @Column(name = "name")
    private String name;


    @Email(message = "Pole email musi być poprawnym adresem e-mail")
    @Column(name = "email")
    private String email;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(idUser, user.idUser) && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idUser, this.name, this.email);
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", surname='" + email + '\'' +
                '}';
    }
}
