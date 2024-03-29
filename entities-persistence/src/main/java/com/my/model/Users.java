package com.my.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by marcin on 15.05.15.
 */
@NamedQueries({
        @NamedQuery(name = "Users.findByLastName",
                query = "Select u from Users u where u.lastName = :ln")   // : ln - parametr do zapytania
})
@Entity
@Table(name="Users", schema ="APP")
public class Users implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Integer id;
    String firstName;
    String lastName;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Integer getId() {
        return id;
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
}
