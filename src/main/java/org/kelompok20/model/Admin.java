package org.kelompok20.model;
public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "Admin"); 
    }
    public Admin() {
        super();
        this.setRole("Admin");
    }
}
