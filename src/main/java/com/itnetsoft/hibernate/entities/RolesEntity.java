package com.itnetsoft.hibernate.entities;

import com.itnetsoft.enums.RoleType;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "roles")
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy = "userRole")
    private Set<UsersEntity> users;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Set<UsersEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersEntity> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesEntity that = (RolesEntity) o;

        if (id != that.id) return false;
        if (!users.equals(that.users)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + users.hashCode();
        return result;
    }
}
