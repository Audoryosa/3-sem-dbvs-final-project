package com.auku.agentura.entity;

import java.sql.Date;

public class Agent {
    private int id;
    private String agency;
    private int experience;
    private Date dateOfBirth;
    private String name;
    private String surname;

    public Agent() {

    }

    public Agent(int id, String agency, int experience, Date dateOfBirth, String name, String surname) {
        this.id = id;
        this.agency = agency;
        this.experience = experience;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
