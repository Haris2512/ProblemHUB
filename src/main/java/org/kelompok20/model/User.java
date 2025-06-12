package org.kelompok20.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Kelas User merepresentasikan entitas pengguna dalam aplikasi.
// Ini adalah bentuk penerapan Encapsulation, dimana:
// - Field (username, password, role) dideklarasikan sebagai 'private' untuk melindungi data.
// - Akses dan modifikasi field dilakukan melalui metode 'public' (getter dan setter).
// Anotasi Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor) secara otomatis
// menghasilkan getter, setter, konstruktor tanpa argumen, dan konstruktor dengan semua argumen,
// sehingga mengurangi boilerplate code.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private String role; // Role pengguna: "Warga" atau "Admin"
}
