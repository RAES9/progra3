package com.company.Views;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.company.Models.Contact;
import com.company.Models.Contacts;
import com.google.gson.Gson;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class ContactsView extends JFrame {
    private JPanel frame;
    private JTextField name;
    private JTextField lastName;
    private JTextField number;
    private JTextField anotherNumber;
    private JTextField email;
    private JComboBox gender;
    private JButton selectButton;
    private JButton saveButton;
    private JScrollPane table1;
    private JButton deleteButton;
    private final Gson gson = new Gson();
    private Contacts c = new Contacts();
    private String defaultPath = "";
    private final JTable table = new JTable();

    public ContactsView(){
        //Set bounds for window and add panel to JFrame
        setBounds(0,0,500,500);
        add(frame);
        deleteButton.setVisible(false);
        setTitle("Contactos");

        //Listener select contact
        selectButton.addActionListener(e -> {
            //Select archive with contacts
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecciona el archivo de tus contactos");
            int result = chooser.showOpenDialog(chooser);
            if (result == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                loadFile(path);
            }
        });

        //Listener save new contact
        saveButton.addActionListener(e -> {
            //Validate if path exist else select direction
            if (defaultPath.isEmpty()) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Donde deseas guardar tus contactos");
                int result = chooser.showSaveDialog(chooser);
                if (result == JFileChooser.APPROVE_OPTION && !chooser.getSelectedFile().getName().isEmpty()) {
                    defaultPath = chooser.getSelectedFile().getAbsolutePath() + ".json";
                    saveFile();
                }
            }else{
                saveFile();
            }
        });

        //Detect click on table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1 && table.getSelectedRows().length > 0){
                    deleteButton.setVisible(true);
                }
            }
        });

        //Delete contact
        deleteButton.addActionListener(e -> {
            int alert = okAndCancelAlert("¿Estas seguro?");
            //Delete rows selected
            int aux = 0;
            if (alert == JOptionPane.YES_OPTION){
                for (int row : table.getSelectedRows()){
                    deleteContact(row - aux);
                    aux++;
                }
                //Update file
                writeFile();
                //Update view
                loadTable();
                table.clearSelection();
                deleteButton.setVisible(false);
            }
        });
    }

    private void saveFile(){
        //Create new contact
        Contact newContact = new Contact();
        newContact.setName(name.getText());
        newContact.setLastName(lastName.getText());
        newContact.setNumber(number.getText());
        newContact.setAnotherNumber(anotherNumber.getText());
        newContact.setEmail(email.getText());
        newContact.setGender(Objects.requireNonNull(gender.getSelectedItem()).toString());
        //Create let from Contacts instance
        final LinkedList<Contact> tempContact = c.getContacts();
        //Add new contact to model
        tempContact.add(newContact);
        //Update Contacts instance
        c.setContacts(tempContact);
        writeFile();
        //Reload table
        loadTable();
    }

    private void loadFile(String path){
        defaultPath = path;
        final String content = usingBufferedReader(path);
        //Convert JSON to Contacts and update Contacts instance
        c = gson.fromJson(content, Contacts.class);
        //Reload table
        loadTable();
    }

    //Delete contact with row
    private void deleteContact(int row){
        final LinkedList<Contact> tempContact = c.getContacts();
        tempContact.remove(row);
        c.setContacts(tempContact);
    }

    //Configure table
    private void loadTable(){
        //Create model for table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Número");
        model.addColumn("Otro Número");
        model.addColumn("Email");
        model.addColumn("Género");
        for (Contact contact : c.getContacts()){
            model.addRow(new Object[]{contact.getName(),contact.getLastName(),contact.getNumber(),contact.getAnotherNumber(),contact.getEmail(),contact.getGender()});
        }
        //Add model to table
        table.setModel(model);
        //Add table to ScrollPane
        table1.getViewport().add(table);
    }

    //Write content
    private void writeFile(){
        //Convert contacts to JSON
        final String jsonString = gson.toJson(c);
        //Write json string in file and save in selected path
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(defaultPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            assert myWriter != null;
            myWriter.write(jsonString);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            myWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Get string content from file
    private static String usingBufferedReader(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    //Create alert
    public static int okAndCancelAlert(String theMessage) {
        return JOptionPane.showConfirmDialog(null, theMessage,
                "Eliminar contactos", JOptionPane.YES_NO_OPTION);
    }
}
