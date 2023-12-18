import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentManagementSystemGUI extends Application {
    private StudentManagementSystem sms = new StudentManagementSystem();
    private TableView<Student> tableView = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management System");

        // Create UI components-----
        Button addButton = new Button("Add Student");
        Button removeButton = new Button("Remove Student");
        Button searchButton = new Button("Search Student");
        Button displayButton = new Button("Display All Students");

        // Set event handlers------
        addButton.setOnAction(e -> showAddStudentDialog());
        removeButton.setOnAction(e -> showRemoveStudentDialog());
        searchButton.setOnAction(e -> showSearchStudentDialog());
        displayButton.setOnAction(e -> displayAllStudents());

        // Create layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        GridPane.setConstraints(addButton, 0, 0);
        GridPane.setConstraints(removeButton, 1, 0);
        GridPane.setConstraints(searchButton, 2, 0);
        GridPane.setConstraints(displayButton, 3, 0);
        GridPane.setConstraints(tableView, 0, 1, 4, 1);

        grid.getChildren().addAll(addButton, removeButton, searchButton, displayButton, tableView);

        // Set up the table view
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, Number> rollNumberColumn = new TableColumn<>("Roll Number");
        rollNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rollNumber"));

        TableColumn<Student, String> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        tableView.getColumns().addAll(nameColumn, rollNumberColumn, gradeColumn);

        // Set up the scene
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void showAddStudentDialog() {
        // Placeholder for adding a student via dialog
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add Student");
        dialog.setHeaderText("Enter student details:");

        // Create UI components for the dialog
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label rollNumberLabel = new Label("Roll Number:");
        TextField rollNumberField = new TextField();

        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField();

        // Set up the layout
        GridPane dialogGrid = new GridPane();
        dialogGrid.setHgap(10);
        dialogGrid.setVgap(10);
        dialogGrid.add(nameLabel, 0, 0);
        dialogGrid.add(nameField, 1, 0);
        dialogGrid.add(rollNumberLabel, 0, 1);
        dialogGrid.add(rollNumberField, 1, 1);
        dialogGrid.add(gradeLabel, 0, 2);
        dialogGrid.add(gradeField, 1, 2);

        dialog.getDialogPane().setContent(dialogGrid);

        // Set up the buttons
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Set up the result converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                // Validate input and create a new Student
                String name = nameField.getText();
                int rollNumber = Integer.parseInt(rollNumberField.getText());
                String grade = gradeField.getText();

                // Create and return a new Student
                return new Student(name, rollNumber, grade);
            }
            return null;
        });

        // Show the dialog and process the result
        dialog.showAndWait().ifPresent(student -> sms.addStudent(student));
        displayAllStudents(); // Refresh the table view
    }

    private void showRemoveStudentDialog() {
        // Placeholder for removing a student via dialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove Student");
        dialog.setHeaderText("Enter Roll Number to Remove:");

        dialog.showAndWait().ifPresent(rollNumber -> {
            try {
                int rollToRemove = Integer.parseInt(rollNumber);
                sms.removeStudent(rollToRemove);
                displayAllStudents(); // Refresh the table view
            } catch (NumberFormatException e) {
                // Handle invalid input
                showAlert("Invalid Input", "Please enter a valid Roll Number.");
            }
        });
    }

    private void showSearchStudentDialog() {
        // Placeholder for searching a student via dialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Student");
        dialog.setHeaderText("Enter Roll Number to Search:");

        dialog.showAndWait().ifPresent(rollNumber -> {
            try {
                int rollToSearch = Integer.parseInt(rollNumber);
                Student foundStudent = sms.searchStudent(rollToSearch);
                if (foundStudent != null) {
                    showAlert("Student Found", foundStudent.toString());
                } else {
                    showAlert("Student Not Found", "No student found with the given Roll Number.");
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
                showAlert("Invalid Input", "Please enter a valid Roll Number.");
            }
        });
    }

    private void displayAllStudents() {
        // Clear the table view
        tableView.getItems().clear();

        // Add all students to the table view
        tableView.getItems().addAll(sms.getStudents());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Student class--------------
    public static class Student {
        private final SimpleStringProperty name;
        private final SimpleIntegerProperty rollNumber;
        private final SimpleStringProperty grade;

        public Student(String name, int rollNumber, String grade) {
            this.name = new SimpleStringProperty(name);
            this.rollNumber = new SimpleIntegerProperty(rollNumber);
            this.grade = new SimpleStringProperty(grade);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public int getRollNumber() {
            return rollNumber.get();
        }

        public SimpleIntegerProperty rollNumberProperty() {
            return rollNumber;
        }

        public String getGrade() {
            return grade.get();
        }

        public SimpleStringProperty gradeProperty() {
            return grade;
        }
    }

    // StudentManagementSystem class
    public static class StudentManagementSystem {
        private ObservableList<Student> students;

        public StudentManagementSystem() {
            this.students = FXCollections.observableArrayList();
        }

        public void addStudent(Student student) {
            students.add(student);
        }

        public void removeStudent(int rollNumber) {
            students.removeIf(student -> student.getRollNumber() == rollNumber);
        }

        public Student searchStudent(int rollNumber) {
            for (Student student : students) {
                if (student.getRollNumber() == rollNumber) {
                    return student;
                }
            }
            return null;
        }

        public ObservableList<Student> getStudents() {
            return students;
        }
    }

}