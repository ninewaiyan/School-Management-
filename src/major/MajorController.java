package major;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import student.Student;

public class MajorController implements Initializable{

    @FXML
    private ComboBox<String> comboboxMajor;

    @FXML
    private TableColumn<Student,String> dob;

    @FXML
    private TableColumn<Student,String> email;

    @FXML
    private TableColumn<Student,String> firstname;

    @FXML
    private TableColumn<Student,Long> id;

    @FXML
    private TableColumn<Student,String> imageName;

    @FXML
    private TableColumn<Student,String> lastname;

    @FXML
    private TableColumn<Student,String> phone;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student,Integer> year;
    
    private final MajorDAO majorDAO = new MajorDAO();
    
    private List<Major> majors;

    @FXML
    void processChooseMajor(ActionEvent event) {
    	String selectedMajor = comboboxMajor.getValue();
    	
    	Major currentMajor = null;
    	for(final Major major : majors) {
    		if(major.getName().equals(selectedMajor)) {
    			currentMajor = major;
    			break;
    		}
    	}
    	
    	studentTable.setItems(majorDAO.getAllStudents(currentMajor.getId()));
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		id.setCellValueFactory(new PropertyValueFactory<Student,Long>("id"));
		firstname.setCellValueFactory(new PropertyValueFactory<Student,String>("firstname"));
		lastname.setCellValueFactory(new PropertyValueFactory<Student,String>("lastname"));
		phone.setCellValueFactory(new PropertyValueFactory<Student,String>("phone"));
		imageName.setCellValueFactory(new PropertyValueFactory<Student,String>("imageName"));
		year.setCellValueFactory(new PropertyValueFactory<Student,Integer>("year"));
		email.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
		dob.setCellValueFactory(new PropertyValueFactory<Student,String>("dob"));
		
		majors = majorDAO.getAllMajors();
		ObservableList<String> majorNames = FXCollections.observableArrayList();
		for(final Major major : majors) {
			majorNames.add(major.getName());
		}
		
		comboboxMajor.setItems(majorNames);
	}

}
