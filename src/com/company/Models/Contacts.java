package com.company.Models;
import java.util.LinkedList;

//Contacts class setters and getters
public class Contacts {
    LinkedList<Contact> contacts = new LinkedList<>();
    public Contacts(){}

    public void setContacts(LinkedList<Contact> contacts) {
        this.contacts = contacts;
    }

    public LinkedList<Contact> getContacts() {
        return contacts;
    }

}
