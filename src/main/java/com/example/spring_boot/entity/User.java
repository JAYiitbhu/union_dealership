package com.example.spring_boot.entity;


public class User {
    
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private java.util.Set<Role> roles;

    public User() {       
    }
    
    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
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
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public java.util.Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(java.util.Set<Role> roles) {
        this.roles = roles;
    }
}
