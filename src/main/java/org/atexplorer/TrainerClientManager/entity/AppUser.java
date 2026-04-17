package org.atexplorer.TrainerClientManager.entity;

import jakarta.persistence.*;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String authority;

    public AppUser(long id, String username, String password, String authority) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public AppUser(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public AppUser() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
