/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author ahmad
 */
public class Employee {

    private int id;
    private String fName;
    private String lName;
    private String title;
    private String userName;
    private String password;
    private String eType;//A for Admin M for Markting I for inventory S for sales 

    public Employee() {
    }

    public Employee(String fName, String lName, String userName, String password, String eType) {
        this.fName = fName;
        this.lName = lName;
        this.userName = userName;
        this.password = password;
        this.eType = eType;
        this.title = this.fName + this.lName;
    }

    public Employee(int id, String fName, String lName, String userName, String password, String eType) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.userName = userName;
        this.password = password;
        this.eType = eType;
        this.title = this.fName + " " + this.lName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

}
