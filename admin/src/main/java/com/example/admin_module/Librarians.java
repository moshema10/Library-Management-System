package com.example.admin_module;
import javafx.beans.property.SimpleStringProperty;


/**
 *  setting string variables in which the data can be stored
 */
public class Librarians {
    //    SimpleStringProperty name;
    SimpleStringProperty id;
    SimpleStringProperty password;
    SimpleStringProperty lastName;
    SimpleStringProperty firstName;


    public Librarians() { //Empty because it is an auto generated constructor
    }

    /**
     * @param id
     * @param pwd
     * @param lname
     * @param fname
     *
     * Here we are creating properties such as id, password, first name, and last name so that it can be displayed on the library management system.
     */
    public Librarians(String id, String pwd, String lname, String fname) {
        this.id = new SimpleStringProperty(id);
        this.password = new SimpleStringProperty(pwd);
        this.lastName = new SimpleStringProperty(lname);
        this.firstName = new SimpleStringProperty(fname);
    }

    /**
     * @return
     *
     * returns the value of ID
     */
    public String getId() {
        return id.get();
    }

    /**
     * @param id
     *
     * describes the parameter
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * @return
     *
     * returns the value of password
     */
    public String getPassword() {
        return password.get();
    }

    /**
     * @param password
     *
     * describes the parameter
     */
    public void setPassword(String password) {
        this.password.set(password);
    }

    /**
     * @return
     *
     * returns the value of last name
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * @param lastName
     *
     * describes the parameter
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * @return
     *
     * describes the parameter
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * @param firstName
     *
     * returns the value of first name
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
}
