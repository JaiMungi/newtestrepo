package com.example1;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class StudentSelector extends JFrame {
    private DefaultListModel<String> availableStudentsModel;
    private DefaultListModel<String> selectedStudentsModel;
    private JList<String> availableStudentsList;
    private JList<String> selectedStudentsList;

   
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentmanagementsystem";
    private static final String USER = "root";
    private static final String PASS = "root";

    public StudentSelector() {
        setTitle("Student Selector");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
       
        availableStudentsModel = new DefaultListModel<>();
        selectedStudentsModel = new DefaultListModel<>();

        loadAvailableStudents();

        availableStudentsList = new JList<>(availableStudentsModel);
        selectedStudentsList = new JList<>(selectedStudentsModel);

        JButton addButton = new JButton(">>");
        JButton removeButton = new JButton("<<");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedStudents(availableStudentsList, availableStudentsModel, selectedStudentsModel);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedStudents(selectedStudentsList, selectedStudentsModel, availableStudentsModel);
            }
        });

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(1, 2));
        listPanel.add(new JScrollPane(availableStudentsList));
        listPanel.add(new JScrollPane(selectedStudentsList));
        //System.out.println("Selected Students List---->"+availableStudentsList);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        add(listPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void loadAvailableStudents() {
          try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM students")) {

            while (rs.next()) {
                String name = rs.getString("name");
                availableStudentsModel.addElement(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }
    private void moveSelectedStudents(JList<String> sourceList, DefaultListModel<String> sourceModel, DefaultListModel<String> targetModel) {
        for (String selectedValue : sourceList.getSelectedValuesList()) {
            targetModel.addElement(selectedValue);
            sourceModel.removeElement(selectedValue);
        }
        System.out.println("Selected Students: " + targetModel.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentSelector frame = new StudentSelector();
            frame.setVisible(true);
        });
    }
}