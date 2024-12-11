package student;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
	private LongProperty id;
	private StringProperty firstname;
	private StringProperty lastname;
	private StringProperty phone;
	private StringProperty email;
	private IntegerProperty year;
	private StringProperty major;
	private StringProperty dob;
	private StringProperty imageName;
	
	public Student(String firstname, String lastname, String phone, String email, Integer year, String major,
			LocalDate dob, String imageName) {
		super();
		this.firstname = new SimpleStringProperty(firstname);
		this.lastname = new SimpleStringProperty(lastname);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.year = new SimpleIntegerProperty(year);
		this.major = new SimpleStringProperty(major);
		this.dob = new SimpleStringProperty(dob.toString());
		this.imageName = new SimpleStringProperty(imageName);
	}

	public Student(Long id, String firstname, String lastname, String phone, String email, Integer year, String major,
			LocalDate dob, String imageName) {
		super();
		this.id = new SimpleLongProperty(id);
		this.firstname = new SimpleStringProperty(firstname);
		this.lastname = new SimpleStringProperty(lastname);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.year = new SimpleIntegerProperty(year);
		this.major = new SimpleStringProperty(major);
		this.dob = new SimpleStringProperty(dob.toString());
		this.imageName = new SimpleStringProperty(imageName);
	}

	public Long getId() {
		return id.get();
	}

	public String getFirstname() {
		return firstname.get();
	}


	public String getLastname() {
		return lastname.get();
	}


	public String getPhone() {
		return phone.get();
	}


	public String getEmail() {
		return email.get();
	}


	public Integer getYear() {
		return year.get();
	}


	public String getMajor() {
		return major.get();
	}


	public LocalDate getDob() {
		return LocalDate.parse(dob.get());
	}


	public String getImageName() {
		return imageName.get();
	}
	
	
	
}
