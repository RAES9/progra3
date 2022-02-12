package com.company.Models;
//Contact class setters and getters
public class Contact {
    private String name;
    private String lastName;
    private String number;
    private String anotherNumber;
    private String email;
    private String gender;
    public Contact(){}

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }

    public String getAnotherNumber() {
        return anotherNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAnotherNumber(String anotherNumber) {
        this.anotherNumber = anotherNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
