package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;

    public LoginView() {
        // Inisialisasi frame
        frame = new JFrame("Sistem Pengaduan - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 220);
        frame.setLocationRelativeTo(null); // Posisi di tengah layar

        // Panel utama dengan border
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Jarak antar komponen

        // Komponen
        JLabel titleLabel = new JLabel("Login Pengaduan Masyarakat");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Role:"), gbc);

        roleCombo = new JComboBox<>(new String[]{"Warga", "Admin"});
        gbc.gridx = 1;
        panel.add(roleCombo, gbc);

        // Tombol
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        // Action listener untuk tombol login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleCombo.getSelectedItem();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Username dan Password harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Nanti panggil AuthController untuk validasi login
                    JOptionPane.showMessageDialog(frame, "Login: " + username + ", Role: " + role, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Action listener untuk tombol register
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Fitur Register belum diimplementasikan.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Tambah panel ke frame dan tampilkan
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView());
    }
}