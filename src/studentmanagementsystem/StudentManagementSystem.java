package studentmanagementsystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class StudentManagementSystem extends Application {

    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CROWNED BRAINS PTI - Student Management System");

        // Main Layout
        BorderPane mainLayout = new BorderPane();
        
        // Scene and Stage setup
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Menu
        MenuBar menuBar = new MenuBar();
        
        // various main menus
        Menu studentMenu = new Menu("Student");
        Menu courseMenu = new Menu("Courses");
        Menu gradeMenu = new Menu("Grades");
        
        //menus under studentMenu
        MenuItem addStudent = new MenuItem("Add Student");
        MenuItem updateStudent = new MenuItem("Update Student");
        MenuItem viewStudentDetails = new MenuItem("View Student Details");
        MenuItem deleteStudent = new MenuItem("Delete Student");
        
        // menus under courseMenu
        MenuItem addCourse = new MenuItem("Add New Course");
        MenuItem enrollCourse = new MenuItem("Enroll Student on Course");
        MenuItem deleteCourse = new MenuItem("Delete Course");
        
        // menus under gradeMenu
        MenuItem assignGrade = new MenuItem("Assign Grade");
        MenuItem updateGrade = new MenuItem("Update Grade");

        // implement the various main menus
        menuBar.getMenus().addAll(studentMenu, courseMenu, gradeMenu);
        
        // implement the various sub menus
        studentMenu.getItems().addAll(addStudent, updateStudent, viewStudentDetails, deleteStudent);
        courseMenu.getItems().addAll(addCourse, enrollCourse, deleteCourse);
        gradeMenu.getItems().addAll(assignGrade, updateGrade);

        // Center Layout for displaying content
        VBox centerLayout = new VBox();
        centerLayout.setSpacing(10);
        centerLayout.setPadding(new Insets(10));

        // Set main layout
        mainLayout.setTop(menuBar);
        mainLayout.setCenter(centerLayout);

        // Event Handlers
        addStudent.setOnAction(e -> showAddStudentForm(centerLayout));
        updateStudent.setOnAction(e -> showUpdateStudentForm(centerLayout));
        //add action to deleteStudent
        viewStudentDetails.setOnAction(e -> showViewStudentDetails(centerLayout));
        //add action for addCourse
        enrollCourse.setOnAction(e -> showEnrollStudentForm(centerLayout));
        //add action for deletCourse
        //add action for assignGrade
        //add action to updateGrade
        

        // Sample courses for demonstration
        courseList.add(new Course("C001", "Mathematics", "Bachelor of Mathematics"));
        courseList.add(new Course("C002", "Science", "Bachelor of Science"));
        courseList.add(new Course("BSCS", "Computer Science", "Bachelor of Science in Computer Science"));
        courseList.add(new Course("BSED", "Education", "Bachelor of Education"));
    }

    private void showAddStudentForm(VBox layout) {
        layout.getChildren().clear();
        Label label = new Label("Add New Student");
        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");
        TextField studentNameField = new TextField();
        studentNameField.setPromptText("Student Name");
        Button addButton = new Button("Add Student");

        addButton.setOnAction(e -> {
            String studentId = studentIdField.getText();
            String studentName = studentNameField.getText();

            if (studentId.isEmpty() || studentName.isEmpty()) {
                showErrorDialog("Both Student ID and Name are required.");
                return;
            }

            Student student = new Student(studentId, studentName);
            studentList.add(student);
            System.out.println("Added student: " + studentName);
            studentIdField.clear();
            studentNameField.clear();
        });

        layout.getChildren().addAll(label, studentIdField, studentNameField, addButton);
    }

    private void showUpdateStudentForm(VBox layout) {
        layout.getChildren().clear();
        Label label = new Label("Update Student Information");
        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");
        TextField studentNameField = new TextField();
        studentNameField.setPromptText("New Student Name");
        Button updateButton = new Button("Update Student");

        updateButton.setOnAction(e -> {
            String studentId = studentIdField.getText();
            String studentName = studentNameField.getText();

            if (studentId.isEmpty() || studentName.isEmpty()) {
                showErrorDialog("Both Student ID and New Name are required.");
                return;
            }

            Student student = findStudentById(studentId);
            if (student != null) {
                student.setName(studentName);
                System.out.println("Updated student ID: " + studentId + " with name: " + studentName);
                studentIdField.clear();
                studentNameField.clear();
            } else {
                showErrorDialog("Student with ID " + studentId + " not found.");
            }
        });

        layout.getChildren().addAll(label, studentIdField, studentNameField, updateButton);
    }

    private Student findStudentById(String id) {
        for (Student student : studentList) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    private void showViewStudentDetails(VBox layout) {
        layout.getChildren().clear();
        Label label = new Label("View Student Details");
        TableView<Student> studentTable = new TableView<>();

        TableColumn<Student, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        studentTable.getColumns().addAll(idColumn, nameColumn);
        studentTable.setItems(studentList);

        layout.getChildren().addAll(label, studentTable);
    }

    private void showEnrollStudentForm(VBox layout) {
        layout.getChildren().clear();
        Label label = new Label("Enroll Student in Course");
        ComboBox<Student> studentComboBox = new ComboBox<>(studentList);
        ComboBox<Course> courseComboBox = new ComboBox<>(courseList);
        Button enrollButton = new Button("Enroll");

        enrollButton.setOnAction(e -> {
            Student student = studentComboBox.getValue();
            Course course = courseComboBox.getValue();

            if (student == null || course == null) {
                showErrorDialog("Please select both a student and a course.");
                return;
            }

            // Enroll student in the course
            System.out.println("Enrolled " + student.getName() + " in course: " + course.getCourseName());
        });

        layout.getChildren().addAll(label, studentComboBox, courseComboBox, enrollButton);
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
