package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardView {
    private JFrame frame;

    public AdminDashboardView() {
        // Inisialisasi frame
        frame = new JFrame("Dashboard Admin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Panel utama
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Judul
        JLabel titleLabel = new JLabel("Dashboard Admin", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Filter
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel("Filter Status:"));
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"Semua", "Belum Diproses", "Diproses", "Selesai"});
        filterPanel.add(statusFilter);
        panel.add(filterPanel, BorderLayout.NORTH);

        // Tabel pengaduan
        String[] columns = {"ID", "Kategori", "Lokasi", "Status", "Aksi"};
        Object[][] data = {
            {"1", "Jalan Rusak", "Jl. Merdeka", "Belum Diproses", "Ubah Status"},
            {"2", "Lampu Mati", "Jl. Sudirman", "Selesai", "Ubah Status"}
        };
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Tombol logout
        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton, BorderLayout.SOUTH);

        // Action listener untuk logout
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginView();
            }
        });

        // Tambah panel ke frame
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboardView());
    }
}