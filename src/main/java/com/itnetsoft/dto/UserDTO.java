package com.itnetsoft.dto;


import java.io.Serializable;
import java.util.Date;

public class UserDTO implements Serializable{
    private int id;
    private String displayName;
    private String email;
    private String firstName;
    private String lastName;
    private String login;
    private Date regDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String toString() {
        return "User:{\n" +
                "id:"+id+"\n" +
                "displayName:"+displayName+"\n" +
                "email:"+email+"\n" +
                "firstName:"+firstName+"\n" +
                "lastName:"+lastName+"\n" +
                "login:"+login+"\n" +
                "regDate:"+regDate+"\n}";
    }
}
