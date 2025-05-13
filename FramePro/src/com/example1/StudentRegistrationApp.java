package com.example1;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class StudentRegistrationApp extends JFrame {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/student_manage?useSSL=false&serverTimezone=UTC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    // Swing components
    private JTextField tfName, tfAge, tfEmail, tfPhone;
    private JComboBox<String> cbGender, cbCourse;
    private JButton btnRegister, btnExport;
    private JTable table;
    private DefaultTableModel tableModel;

    private Connection conn;

    public StudentRegistrationApp() {
        setTitle("Student Registration Form");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        formPanel.setBorder(BorderFactory.createTitledBorder("Register New Student"));

        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0 - Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        tfName = new JTextField(15);
        formPanel.add(tfName, gbc);

        // Row 1 - Age
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        tfAge = new JTextField(5);
        formPanel.add(tfAge, gbc);

        // Row 2 - Gender
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        cbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        formPanel.add(cbGender, gbc);

        // Row 3 - Course
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1;
        cbCourse = new JComboBox<>(new String[]{"Computer Science", "Mathematics", "Physics", "Chemistry", "Biology", "English"});
        formPanel.add(cbCourse, gbc);

        // Row 4 - Email
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        tfEmail = new JTextField(20);
        formPanel.add(tfEmail, gbc);

        // Row 5 - Phone
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        tfPhone = new JTextField(15);
        formPanel.add(tfPhone, gbc);

        // Row 6 - Buttons
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnRegister = new JButton("Register");
        btnExport = new JButton("Export to Excel");
        btnPanel.add(btnRegister);
        btnPanel.add(btnExport);
        formPanel.add(btnPanel, gbc);
        
        add(formPanel, BorderLayout.NORTH);

        // Table to display registered students
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Age", "Gender", "Course", "Email", "Phone"});
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Students"));
        add(scrollPane, BorderLayout.CENTER);

        // Connect to DB and load existing data
        connectToDatabase();
        loadStudentsFromDB();

        // Button listeners
        btnRegister.addActionListener(e -> registerStudent());
        btnExport.addActionListener(e -> exportToExcel());

        // Make UI responsive on smaller screen and avoid resizing issues
        setMinimumSize(new Dimension(700, 500));
    }

    private void connectToDatabase() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // Make sure table exists
            Statement stmt = conn.createStatement();
            String sqlCreate = "CREATE TABLE IF NOT EXISTS students " +
                               "(id INT NOT NULL AUTO_INCREMENT, " +
                               " name VARCHAR(100), " +
                               " age INT, " +
                               " gender VARCHAR(10), " +
                               " course VARCHAR(50), " +
                               " email VARCHAR(100), " +
                               " phone VARCHAR(20), " +
                               " PRIMARY KEY (id))";
            stmt.execute(sqlCreate);
            stmt.close();
        } catch(Exception ex) {
            showError("Database connection error: " + ex.getMessage());
        }
    }

    private void loadStudentsFromDB() {
        try {
            tableModel.setRowCount(0);
            String query = "SELECT * FROM students";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("name"));
                row.add(rs.getInt("age"));
                row.add(rs.getString("gender"));
                row.add(rs.getString("course"));
                row.add(rs.getString("email"));
                row.add(rs.getString("phone"));
                tableModel.addRow(row);
            }
            rs.close();
            pst.close();
        } catch(Exception ex) {
            showError("Error loading data: " + ex.getMessage());
        }
    }

    private void registerStudent() {
        String name = tfName.getText().trim();
        String ageStr = tfAge.getText().trim();
        String gender = cbGender.getSelectedItem().toString();
        String course = cbCourse.getSelectedItem().toString();
        String email = tfEmail.getText().trim();
        String phone = tfPhone.getText().trim();

        // Validation
        if(name.isEmpty() || ageStr.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showError("Please fill in all the required fields.");
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageStr);
            if(age <= 0) {
                showError("Age must be a positive number.");
                return;
            }
        } catch(NumberFormatException e) {
            showError("Invalid age. Please enter a valid number.");
            return;
        }

        try {
            String sqlInsert = "INSERT INTO students (name, age, gender, course, email, phone) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sqlInsert);
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, gender);
            pst.setString(4, course);
            pst.setString(5, email);
            pst.setString(6, phone);
            int affectedRows = pst.executeUpdate();
            pst.close();
            if(affectedRows > 0) {
                showMessage("Student registered successfully.");
                clearForm();
                loadStudentsFromDB();
            } else {
                showError("Registration failed.");
            }
        } catch(Exception ex) {
            showError("Error in registration: " + ex.getMessage());
        }
    }

    private void clearForm() {
        tfName.setText("");
        tfAge.setText("");
        cbGender.setSelectedIndex(0);
        cbCourse.setSelectedIndex(0);
        tfEmail.setText("");
        tfPhone.setText("");
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Excel file");
        fileChooser.setSelectedFile(new File("StudentsReport.xlsx"));
        int userSelection = fileChooser.showSaveDialog(this);

        if(userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Students");

                // Header row
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(tableModel.getColumnName(i));
                    CellStyle style = workbook.createCellStyle();
                   Font font = workbook.createFont();
                   font.setBold(true);
                    style.setFont(font);
                    cell.setCellStyle(style);
                }

                // Data rows
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = tableModel.getValueAt(i, j);
                        if(value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        } else if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }

                // Autosize columns
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                    workbook.write(fos);
                }

                showMessage("Report exported successfully to " + fileToSave.getAbsolutePath());
            } catch(Exception ex) {
                showError("Error exporting report: " + ex.getMessage());
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Load MySQL driver and Apache POI libs should be in classpath
        SwingUtilities.invokeLater(() -> {
            StudentRegistrationApp app = new StudentRegistrationApp();
            app.setVisible(true);
        });
    }
}

