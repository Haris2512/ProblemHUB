package org.kelompok20.model;

// Kelas Admin mewarisi (inherits) dari kelas User.
// Ini adalah penerapan Inheritance, dimana Admin adalah tipe khusus dari User.
// Admin akan memiliki semua properti (username, password, role) dan perilaku dari User,
// serta dapat memiliki atribut atau metode khusus Admin jika diperlukan di masa depan.
// Catatan Penting dari Ketentuan Proyek: Pewarisan dari kelas JavaFX (seperti Application)
// tidak dihitung sebagai pilar OOP. Namun, inheritance Admin extends User ini sah dihitung.
public class Admin extends User {
    // Konstruktor untuk Admin dengan username dan password. Role akan otomatis diset sebagai "Admin".
    public Admin(String username, String password) {
        super(username, password, "Admin"); // Memanggil konstruktor superclass (User)
    }

    // Konstruktor default untuk Admin (jika diperlukan oleh Jackson atau framework lain).
    // Memastikan role default-nya adalah "Admin".
    public Admin() {
        super();
        this.setRole("Admin");
    }
}
