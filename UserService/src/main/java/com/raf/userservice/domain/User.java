package com.raf.userservice.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "korisnik")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnik_id")
    private Long id;
    private String email;
    @Column(name="ime")
    private String firstName;
    @Column(name="prezime")
    private String lastName;
    private String username;
    private String password;
    @Column(name="telefon")
    private String phone;
    @Column(name="datum_rodjenja")
    private String birthDate;
    @Column(name="aktivan")
    private String active;
    private String role;
    @Column(name="broj_rezervacija")
    private Integer reservationNum;
    @Column(name="broj_pasosa")
    private Integer passportNum;
    @Column(name="datum_zaposlenja")
    private String hireDate;

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getReservationNum() {
        return reservationNum;
    }

    public void setReservationNum(Integer reservationNum) {
        this.reservationNum = reservationNum;
    }

    public Integer getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(Integer passportNum) {
        this.passportNum = passportNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

