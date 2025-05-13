package com.example1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class AddStudent extends javax.swing.JFrame {
	final List<String> studentNames = new ArrayList<>();
	String studentName=null;

    DefaultListModel<String> model1 = new DefaultListModel<>();
    DefaultListModel<String> model2 = new DefaultListModel<>();

    // Constructor
    public AddStudent() {
        initComponents();
        setupDragAndDrop();
        setSize(600, 460);
        setLocationRelativeTo(null);
        setVisible(true);
        loadStudent();
    }

    private void setupDragAndDrop() {
        jList1.setDragEnabled(true);
        jList1.setDropMode(javax.swing.DropMode.INSERT);
        jList1.setTransferHandler(new javax.swing.TransferHandler("selectedValue"));

        jList2.setDragEnabled(true);
        jList2.setDropMode(javax.swing.DropMode.INSERT);
        jList2.setTransferHandler(new javax.swing.TransferHandler("selectedValue"));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jList1.setModel(model1);
        jScrollPane1.setViewportView(jList1);

        jPanel1.setLayout(new javax.swing.GroupLayout(jPanel1));
        jPanel1.add(jScrollPane1);
        jPanel1.setBounds(20, 50, 180, 220);
        getContentPane().add(jPanel1);

        jList2.setModel(model2);
        jScrollPane2.setViewportView(jList2);

        jPanel2.setLayout(new javax.swing.GroupLayout(jPanel2));
        jPanel2.add(jScrollPane2);
        jPanel2.setBounds(360, 50, 167, 220);
        getContentPane().add(jPanel2);

        jButton1.setText("Add");
        jButton1.setBounds(240, 190, 75, 30);
        getContentPane().add(jButton1);

        jButton2.setText("Back");
        jButton2.setBounds(240, 230, 75, 30);
        getContentPane().add(jButton2);

        jLabel1.setIcon(new ImageIcon("src/images/BackgroundProject.jpeg"));  // Better path
        jLabel1.setText("");
        jLabel1.setBounds(0, 0, 580, 420);
        getContentPane().add(jLabel1);

        pack();

        // ActionListener for "Add" button
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveSelectedStudent();
            }
        });

        // ActionListener for "Back" button
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           //     FacultyLogin facultyLogin = new FacultyLogin();
               // facultyLogin.setVisible(true);

                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(jButton2);
                if (parentFrame != null) {
                    parentFrame.dispose();
                }
            }
        });
    }

    private void moveSelectedStudent() {
        String selectedStudent = jList1.getSelectedValue();
        if (selectedStudent != null) {
            model1.removeElement(selectedStudent);
            model2.addElement(selectedStudent);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to add!");
        }
    }

    private void loadStudent() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        // Use a List to hold the student names
        
        try {
            // Step 1: Connect to the database and retrieve the student names
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagementsystem", "root", "root");

           // stmt = conn.createStatement();
            //  Statement stmt = conn.createStatement();
           // String sql = "SELECT name FROM students";
           // rs = stmt.executeQuery(sql);

            // Step 2: Populate the List with student names from the ResultSet
//            while (rs.next()) {
//                //String studentName = rs.getString("name");
//                studentName = rs.getString("name");
//                studentNames.add(studentName); // Store names in the list
//                
//            }

            // Step 3: Update the UI in the EDT (Swing thread)
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        // Clear existing data before loading new data into the model
                      //  model1.clear();
                      //  model2.clear();

                        // Add all student names to the JList model
                      //  for (String studentName : studentNames) {
                    	System.out.println("------>>>>"+studentName);
                            //model1.addElement(studentName);
                    	//jList1.add((Component) studentNames);
                    
                     //   };
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(AddStudent.this, "Error while updating UI: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for more detailed debugging
        } finally {
            // Ensure the database resources are closed properly in the 'finally' block
            try {
                if (rs != null) rs.close();
                //if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }






    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AddStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddStudent();
            }
        });
    }

    // Variables declaration
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
}
