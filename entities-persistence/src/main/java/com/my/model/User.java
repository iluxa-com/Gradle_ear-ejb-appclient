package com.my.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by marcin on 15.05.15.
 */
@Entity
@Table(name="User")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    String firstName;
    String lastName;

}
