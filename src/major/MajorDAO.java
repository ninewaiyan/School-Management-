package major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import student.Student;

public class MajorDAO {
	private Connection connection;
	private Statement stmt;
	private ResultSet rs;
	
	private void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<Major> getAllMajors(){
		List<Major> majorList = new ArrayList<Major>();
		connection = DataBaseConnection.getConnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from major;");
			
			while (rs.next()) {
				majorList.add(new Major(
						rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("description")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return majorList;
	}
	
	
	
	public ObservableList<Student> getAllStudents(Integer majorId){
		ObservableList<Student> studentList = FXCollections.observableArrayList();
		connection = DataBaseConnection.getConnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from student where major_id='"+majorId+"';");
			
			while (rs.next()) {
				studentList.add(new Student(
						rs.getLong("id"), 
						rs.getString("firstname"), 
						rs.getString("lastname"), 
						rs.getString("phone"), 
						rs.getString("email"), 
						rs.getInt("year"), 
						rs.getString("major"), 
						rs.getDate("dob").toLocalDate(), 
						rs.getString("image_name")));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return studentList;
	}

}
