package org.kelompok20.model;

// Untuk saat ini, Admin bisa diwakili oleh User dengan role "Admin".
// Jika ada atribut atau perilaku khusus Admin yang tidak ada di User,
// Anda bisa menambahkannya di sini atau extend kelas User.
public class Admin extends User {
    // Contoh jika ada atribut khusus admin
    // private String departemen;

    public Admin(String username, String password) {
        super(username, password, "Admin");
        // this.departemen = departemen;
    }

    public Admin() {
        super();
        this.setRole("Admin"); // Pastikan role default-nya Admin
    }
}