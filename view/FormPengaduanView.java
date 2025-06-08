package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPengaduanView {
    private JFrame frame;
    private JTextField lokasiField;
    private JComboBox<String> kategoriCombo;
    private JTextArea deskripsiArea;
    private JLabel fotoLabel;

    public FormPengaduanView() {
        // Inisialisasi frame
        frame = new JFrame("Form Pengaduan");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);

        // Panel utama
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Komponen
        JLabel titleLabel = new JLabel("Form Pengaduan Masyarakat");
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
        panel.add(new JLabel("Kategori:"), gbc);

        kategoriCombo = new JComboBox<>(new String[]{"Jalan Rusak", "Lampu Mati", "Saluran Tersumbat", "Lainnya"});
        gbc.gridx = 1;
        panel.add(kategoriCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Lokasi:"), gbc);

        lokasiField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(lokasiField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Deskripsi:"), gbc);

        deskripsiArea = new JTextArea(3, 20);
        deskripsiArea.setLineWrap(true);
        gbc.gridx = 1;
        panel.add(new JScrollPane(deskripsiArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Foto (Opsional):"), gbc);

        fotoLabel = new JLabel("Belum ada foto");
        gbc.gridx = 1;
        panel.add(fotoLabel, gbc);

        JButton uploadButton = new JButton("Unggah Foto");
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(uploadButton, gbc);

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(submitButton, gbc);

        JButton cancelButton = new JButton("Cancel");
        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        // Action listener untuk upload foto
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    fotoLabel.setText(fileChooser.getSelectedFile().getName());
                    // Nanti panggil FileHandler untuk simpan foto
                }
            }
        });

        // Action listener untuk submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String kategori = (String) kategoriCombo.getSelectedItem();
                String lokasi = lokasiField.getText();
                String deskripsi = deskripsiArea.getText();

                if (lokasi.isEmpty() || deskripsi.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Lokasi dan Deskripsi harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Nanti panggil PengaduanController untuk simpan pengaduan
                    JOptionPane.showMessageDialog(frame, "Pengaduan dikirim: " + kategori + ", " + lokasi);
                    frame.dispose();
                }
            }
        });

        // Action listener untuk cancel
        cancelButton.addActionListener(e -> frame.dispose());

        // Tambah panel ke frame
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPengaduanView());
    }
}