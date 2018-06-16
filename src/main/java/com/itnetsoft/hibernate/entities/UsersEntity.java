package com.itnetsoft.hibernate.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="first_name", nullable = false, length=80)
    private String firstName;

    @Column(name="last_name", length=80)
    private String lastName;

    @Column(name="username", nullable = false, unique = true, length=30)
    private String login;

    @Column(name="display_name", unique = true, length=30)
    private String displayName;

    @Column(name="email", nullable = false, unique = true, length=70)
    private String email;

    @Column(name="password", nullable = false,  length=64)
    private String password;

    @Column(name="reg_date", updatable = false, insertable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @OneToMany(mappedBy = "author")
    private List<ContentEntity> contentList;

    @ManyToOne
    @JoinColumn(name="role_id", referencedColumnName = "id")
    private RolesEntity userRole;

    @Column(name = "enabled", nullable=false,updatable = false, insertable = true)
    private int enabled = 1;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public List<ContentEntity> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentEntity> contentList) {
        this.contentList = contentList;
    }

    public RolesEntity getUserRole() {
        return userRole;
    }

    public void setUserRole(RolesEntity userRole) {
        this.userRole = userRole;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String toString() {
        return "User:{\n" +
                "id:"+id+"\n" +
                "displayName:"+displayName+"\n" +
                "email:"+email+"\n" +
                "firstName:"+firstName+"\n" +
                "lastName:"+lastName+"\n" +
                "login:"+login+"\n" +
                "password:"+password+"\n"+
                "regDate:"+regDate+"\n}";
    }
}
