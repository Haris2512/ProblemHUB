package org.kelompok20.controller;

import org.kelompok20.model.User;
import org.kelompok20.utils.JsonDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthController {
    private static final String USERS_FILE = "data/users.json"; 
    private JsonDataManager<User> userDataManager;
    private static User currentLoggedInUser = null;

    public AuthController() {
        userDataManager = new JsonDataManager<>(USERS_FILE, User.class);
        if (userDataManager.loadData().isEmpty()) {
            List<User> defaultUsers = new ArrayList<>();
            defaultUsers.add(new User("admin", "admin123", "Admin"));
            defaultUsers.add(new User("warga1", "warga123", "Warga"));
            defaultUsers.add(new User("warga2", "warga123", "Warga"));
            defaultUsers.add(new User("warga3", "warga123", "Warga"));
            userDataManager.saveData(defaultUsers);
        }
    }

    public Optional<User> login(String username, String password, String role) {
        List<User> users = userDataManager.loadData();
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password) &&
                user.getRole().equals(role)) {
                currentLoggedInUser = user;
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean register(String username, String password, String role) {
        List<User> users = userDataManager.loadData();
        boolean usernameExists = users.stream().anyMatch(u -> u.getUsername().equals(username));
        if (usernameExists) {
            return false;
        }

        User newUser = new User(username, password, role);
        users.add(newUser);
        userDataManager.saveData(users);
        return true;
    }

    public void logout() {
        currentLoggedInUser = null;
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }
}