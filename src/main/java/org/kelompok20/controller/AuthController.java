package org.kelompok20.controller;

import org.kelompok20.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthController {
    // Dummy data user untuk simulasi (ganti dengan database di aplikasi nyata)
    private static List<User> users = new ArrayList<>();
    private static User currentLoggedInUser = null; // Menyimpan user yang sedang login

    static {
        // Tambahkan beberapa user default
        users.add(new User("warga", "warga123", "Warga"));
        users.add(new User("admin", "admin123", "Admin"));
    }

    public Optional<User> login(String username, String password, String role) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password) &&
                user.getRole().equals(role)) {
                currentLoggedInUser = user; // Set user yang sedang login
                return Optional.of(user); // Mengembalikan Optional berisi user
            }
        }
        return Optional.empty(); // Mengembalikan Optional kosong jika login gagal
    }

    public boolean register(String username, String password, String role) {
        if (!role.equalsIgnoreCase("Warga")) {
            return false; // Gagal kalau bukan Warga
        }


        // Cek apakah username sudah ada
        boolean usernameExists = users.stream().anyMatch(u -> u.getUsername().equals(username));
        if (usernameExists) {
            return false; // Registrasi gagal, username sudah ada
        }


        User newUser = new User(username, password, "Warga");
        User newUser = new User(username, password, role);

        users.add(newUser);
        return true; // Registrasi berhasil
    }

    public void logout() {
        currentLoggedInUser = null; // Hapus user yang sedang login
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }
}