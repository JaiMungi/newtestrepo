package com.example1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentSelector1 extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASS = "your_password";

    public StudentSelector1() {
        setTitle("Student Selector");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table model and table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age"}, 0);
        table = new JTable(tableModel);
        
        // Wrap the table in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Load button
        loadButton = new JButton("Load Students");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStudents();
            }
        });

        // Layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(loadButton, BorderLayout.SOUTH);
    }

    private void loadStudents() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Connect to the database and retrieve student data
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagementsystem", "root", "root");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                String roll = rs.getString("rollnumber");
                tableModel.addRow(new Object[]{id, name, roll});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentSelector1 selector = new StudentSelector1();
            selector.setVisible(true);
        });
    }
}
