package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WargaDashboardView {
    private JFrame frame;

    public WargaDashboardView() {
        // Inisialisasi frame
        frame = new JFrame("Dashboard Warga");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        // Panel utama
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Judul
        JLabel titleLabel = new JLabel("Dashboard Warga", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Tabel riwayat pengaduan
        String[] columns = {"ID", "Kategori", "Lokasi", "Status"};
        Object[][] data = {
            {"1", "Jalan Rusak", "Jl. Merdeka", "Diproses"},
            {"2", "Lampu Mati", "Jl. Sudirman", "Selesai"}
        };
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton newPengaduanButton = new JButton("Buat Pengaduan Baru");
        JButton logoutButton = new JButton("Logout");
        buttonPanel.add(newPengaduanButton);
        buttonPanel.add(logoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener untuk buat pengaduan baru
        newPengaduanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormPengaduanView();
            }
        });

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
        SwingUtilities.invokeLater(() -> new WargaDashboardView());
    }
}