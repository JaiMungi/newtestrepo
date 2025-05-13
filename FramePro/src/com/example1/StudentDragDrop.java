package com.example1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

public class StudentDragDrop extends JFrame {
    private static DefaultListModel<String> model1 = new DefaultListModel<>();
    private static DefaultListModel<String> model2 = new DefaultListModel<>();
    private JList<String> jList1 = new JList<>(model1);
    private JList<String> jList2 = new JList<>(model2);
    private static JList<String> availableStudentsList;
    private static JList<String> selectedStudentsList;
    static JButton addButton;
    static JButton removeButton;

    public StudentDragDrop() {
        setupUI();
        setupDragAndDrop();
        loadStudentsFromDatabase();
    }

    private void setupUI() {
        setTitle("Student Drag and Drop");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JScrollPane scrollPane1 = new JScrollPane(jList1);
        JScrollPane scrollPane2 = new JScrollPane(jList2);
        scrollPane1.setBounds(50, 50, 200, 250);
        scrollPane2.setBounds(350, 50, 200, 250);
        addButton = new JButton(">>");
        addButton.setBounds(240, 130, 100, 30);
        removeButton = new JButton("<<");
        removeButton.setBounds(240, 190, 100, 30);
        JButton backButton = new JButton("Back");
        backButton.setBounds(250, 320, 100, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the application
            }
        });

        add(scrollPane1);
        add(scrollPane2);
        add(backButton);
       
        add(addButton);
        add(removeButton);
    }

    private void setupDragAndDrop() {
        jList1.setDragEnabled(true);
        jList1.setDropMode(DropMode.INSERT);
        jList1.setTransferHandler(new TransferHandler("text"));

        jList2.setDragEnabled(true);
        jList2.setDropMode(DropMode.INSERT);
        jList2.setTransferHandler(new TransferHandler("text"));
    }
    
    private void loadStudentsFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagementsystem", "root", "root");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM students")) {

            while (rs.next()) {
                model1.addElement(rs.getString("name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
       
            StudentDragDrop frame = new StudentDragDrop();
            frame.setVisible(true);
        }
   
    
 }


