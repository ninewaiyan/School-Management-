package student;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class StudentController implements Initializable{

    @FXML
    private ComboBox<String> comboboxMajor;

    @FXML
    private ComboBox<String> comboboxYear;

    @FXML
    private TableColumn<Student,String> dob;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private TableColumn<Student,String> email;

    @FXML
    private TableColumn<Student,String> firstname;

    @FXML
    private TableColumn<Student,Long> id;

    @FXML
    private TableColumn<Student,String> lastname;

    @FXML
    private TableColumn<Student,String> major;

    @FXML
    private TableColumn<Student,String> phone;

    @FXML
    private ImageView studentImageView;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFirstname;

    @FXML
    private TextField tfLastname;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfSearch;

    @FXML
    private TableColumn<Student,Integer> year;
    
    private final StudentDAO studentDAO = new StudentDAO();
    
    private File selectedFile = null;
    
    private boolean isEditButtonClicked = false;
    private Long studentId = null;

    @FXML
    void processClear(ActionEvent event) {
    	clear();
    }
    
    @FXML
    void processEdit(ActionEvent event) {
    	isEditButtonClicked = true;
    	Student student = studentTable.getSelectionModel().getSelectedItem();
    	studentId = student.getId();
    	tfEmail.setText(student.getEmail());
    	tfFirstname.setText(student.getFirstname());
    	tfLastname.setText(student.getLastname());
    	tfPhone.setText(student.getPhone());
    	comboboxMajor.setValue(student.getMajor().toUpperCase());
    	comboboxYear.setValue(student.getYear().toString());
    	dobDatePicker.setValue(student.getDob());
    	System.out.println(student.getImageName());
    	studentImageView.setImage(new Image(getClass().getResourceAsStream("../img/student/"+student.getImageName())));
    }

    @FXML
    void processDelete(ActionEvent event) {
    	Student student = studentTable.getSelectionModel().getSelectedItem();
    	boolean deletedOK = studentDAO.deleteStudent(student.getId());
    	
    	if(deletedOK) {
    		try {
				Files.delete(Path.of(new File("src/img/student/"+student.getImageName()).toURI()));
				studentTable.setItems(studentDAO.getAllStudents());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}else {
    		System.out.println("deleted fail!");
    	}
    }

    @FXML
    void processImage(MouseEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setInitialDirectory(new File("C:\\Users\\Nine Wai Yan\\Desktop\\studentPhoto"));
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files","*.jpg"));
    	selectedFile = fileChooser.showOpenDialog(null);
    	
    	if(selectedFile != null) {
    		studentImageView.setImage(new Image(selectedFile.toURI().toString()));
    	}
    }

    @FXML
    void processRefresh(ActionEvent event) {
    	studentTable.setItems(studentDAO.getAllStudents());
    }
    
    private void clear() {
    	selectedFile = null;
    	studentId = null;
    	isEditButtonClicked = false;
    	tfEmail.clear();
    	tfFirstname.clear();
    	tfLastname.clear();
    	tfPhone.clear();
    	comboboxMajor.setValue(null);
    	comboboxYear.setValue(null);
    	dobDatePicker.setValue(LocalDate.now());
    	studentImageView.setImage(new Image(getClass().getResourceAsStream("../img/photo.png")));
    }

    @FXML
    void processSave(ActionEvent event) {
    	String firstname = tfFirstname.getText().trim();
    	String lastname = tfLastname.getText().trim();
    	String phone = tfPhone.getText().trim();
    	String email = tfEmail.getText().trim();
    	Integer year = Integer.valueOf(comboboxYear.getValue());
    	String major = comboboxMajor.getValue().toLowerCase();
    	LocalDate dob = dobDatePicker.getValue();
    	String imageName = null;
    	if(selectedFile != null) {
    		imageName = selectedFile.getName();
    	}
    	
    	if(isEditButtonClicked && studentId != null) {
    		String currentImageName = studentDAO.getImageName(studentId);
    		
    		Student student = null;
    		if(imageName != null) {
    			student = new Student(studentId, firstname, lastname, phone, email, year, major, dob, imageName);
    		}else {
    			student = new Student(studentId, firstname, lastname, phone, email, year, major, dob, currentImageName);
    		}
    		boolean updatedOk = studentDAO.updateStudent(student);
    		
    		if(updatedOk) {
    			if(selectedFile != null) {
    				try {
    					Files.delete(Path.of(new File("src/img/student/"+currentImageName).toURI()));
						Files.copy(Path.of(selectedFile.toURI()),Path.of(new File("src/img/student/"+imageName).toURI()),StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}
    	}else {
    		Student student = new Student(firstname, lastname, phone, email, year, major, dob, imageName);
        	boolean createdOk = studentDAO.createStudent(student);
        	
        	if(createdOk) {
        		try {
    				Files.copy(Path.of(selectedFile.toURI()),Path.of(new File("src/img/student/"+imageName).toURI()),StandardCopyOption.REPLACE_EXISTING);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}else {
        		System.out.println("student creation is failed...");
        	}
    	}
    	
    	studentTable.setItems(studentDAO.getAllStudents());
    	clear();
    	
    }

    @FXML
    void processSearch(ActionEvent event) {
//    	String email = tfSearch.getText().trim();
//    	
//    	if(email == null) {
//    		return;
//    	}
//    	
//    	Student student = studentDAO.getStudentByEmail(email);
//    	
//    	if(student == null) {
//    		return;
//    	}
//    	
//    	studentTable.getItems().clear();
//    	studentTable.getItems().add(student);
//    	
    	
    	Long id = Long.valueOf(tfSearch.getText().trim().toString());
    	
    	if(id == null) {
    		return;
    	}
    	
    	Student student = studentDAO.getStudentByID(id);
    	
    	if(student == null) {
    		return;
    	}
    	
    	studentTable.getItems().clear();
    	studentTable.getItems().add(student);
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		ObservableList<String> majors = FXCollections.observableArrayList(
			"IT","CIVIL","EC","ARCHI"
				);
		ObservableList<String> years = FXCollections.observableArrayList(
				"1","2","3","4","5","6"
					);
		comboboxMajor.setItems(majors);
		comboboxYear.setItems(years);
		
		
		id.setCellValueFactory(new PropertyValueFactory<Student,Long>("id"));
		firstname.setCellValueFactory(new PropertyValueFactory<Student,String>("firstname"));
		lastname.setCellValueFactory(new PropertyValueFactory<Student,String>("lastname"));
		phone.setCellValueFactory(new PropertyValueFactory<Student,String>("phone"));
		major.setCellValueFactory(new PropertyValueFactory<Student,String>("major"));
		year.setCellValueFactory(new PropertyValueFactory<Student,Integer>("year"));
		email.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
		dob.setCellValueFactory(new PropertyValueFactory<Student,String>("dob"));
		
		studentTable.setItems(studentDAO.getAllStudents());
	}

}
