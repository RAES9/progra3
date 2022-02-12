package com.company;

import com.company.Views.ContactsView;

public class Main {
    public static void main(String[] args) {
        //Call view and show on screen
        ContactsView contactsView = new ContactsView();
        contactsView.setLocationRelativeTo(null);
        contactsView.setVisible(true);
    }
}
