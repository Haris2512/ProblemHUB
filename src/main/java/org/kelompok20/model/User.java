package org.kelompok20.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Anotasi Lombok untuk getter, setter, equals, hashCode, toString
@NoArgsConstructor // Anotasi Lombok untuk konstruktor tanpa argumen
@AllArgsConstructor // Anotasi Lombok untuk konstruktor dengan semua argumen
public class User {
    private String username;
    private String password;
    private String role; // "Warga" atau "Admin"
}