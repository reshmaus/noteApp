package com.devmountain.noteApp.entities;

import com.devmountain.noteApp.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity//the persistence objects stores as a record in the database,@Entity tells spring that this class is being mapped to a data source
@Table(name = "Users")//This is where you can set what table you want these objects to be mapped to
@Data//generates getter and setters by lambok
@AllArgsConstructor// we are writing these instead of creating constructer as lambook is creating for us.
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//it's a primary key and it's Id gets auto incremented
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    //This will lazily retrive data on request,cascade- will delete the main table and also related tables of it
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference //data is saved in jason format
    private Set<Note> noteSet = new HashSet<>();

    public User(UserDto userDto){
        if (userDto.getUsername() != null){
            this.username = userDto.getUsername();
        }
        if (userDto.getPassword() != null){
            this.password = userDto.getPassword();
        }
    }
}
