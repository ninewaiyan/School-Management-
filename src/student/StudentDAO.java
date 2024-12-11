package student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDAO {
	
	private Connection connection;
	private Statement stmt;
	private PreparedStatement pStmt;
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
	
	//Create C
	public boolean createStudent(Student student) {
		boolean createdOK = false;
		connection = DataBaseConnection.getConnection();
		try {
			pStmt = connection.prepareStatement("insert into student "
					+ "(firstname,lastname,phone,email,major,year,dob,image_name) "
					+ "values(?,?,?,?,?,?,?,?);");
			pStmt.setString(1, student.getFirstname());
			pStmt.setString(2, student.getLastname());
			pStmt.setString(3, student.getPhone());
			pStmt.setString(4, student.getEmail());
			pStmt.setString(5, student.getMajor());
			pStmt.setInt(6, student.getYear());
			pStmt.setDate(7, Date.valueOf(student.getDob()));
			pStmt.setString(8, student.getImageName());
			
			int rowEffected = pStmt.executeUpdate();
			if(rowEffected > 0) createdOK = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return createdOK;
	}
	
	public String getImageName(Long studentId) {
		String imageName = null;
		connection = DataBaseConnection.getConnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select image_name from student where id='"+studentId+"';");
			while(rs.next()) {
				imageName = rs.getString("image_name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return imageName;
	}
	
	
	//Update U
	public boolean updateStudent(Student student) {
		boolean updatedOK = false;
		connection = DataBaseConnection.getConnection();
		try {
			pStmt = connection.prepareStatement("update student set "
					+ "firstname=?,"
					+ "lastname=?,"
					+ "phone=?,"
					+ "email=?,"
					+ "major=?,"
					+ "year=?,"
					+ "dob=?,"
					+ "image_name=? where id=?;");
			pStmt.setString(1, student.getFirstname());
			pStmt.setString(2, student.getLastname());
			pStmt.setString(3, student.getPhone());
			pStmt.setString(4, student.getEmail());
			pStmt.setString(5, student.getMajor());
			pStmt.setInt(6, student.getYear());
			pStmt.setDate(7, Date.valueOf(student.getDob()));
			pStmt.setString(8, student.getImageName());
			pStmt.setLong(9, student.getId());
			
			int rowEffected = pStmt.executeUpdate();
			if(rowEffected > 0) updatedOK = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return updatedOK;
	}
	
	
	
	//getStudentByEmail
	public Student getStudentByEmail(String email){
		Student student = null;
		connection = DataBaseConnection.getConnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from student where email='"+email+"';");
			
			while (rs.next()) {
				student = new Student(
						rs.getLong("id"), 
						rs.getString("firstname"), 
						rs.getString("lastname"), 
						rs.getString("phone"), 
						rs.getString("email"), 
						rs.getInt("year"), 
						rs.getString("major"), 
						rs.getDate("dob").toLocalDate(), 
						rs.getString("image_name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return student;
	}
	
	public Student getStudentByID(Long id){
		Student student = null;
		connection = DataBaseConnection.getConnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from student where id='"+id+"';");
			
			while (rs.next()) {
				student = new Student(
						rs.getLong("id"), 
						rs.getString("firstname"), 
						rs.getString("lastname"), 
						rs.getString("phone"), 
						rs.getString("email"), 
						rs.getInt("year"), 
						rs.getString("major"), 
						rs.getDate("dob").toLocalDate(), 
						rs.getString("image_name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return student;
	}
	
	//Read R
	public ObservableList<Student> getAllStudents(){
		ObservableList<Student> studentList = FXCollections.observableArrayList();
		connection = DataBaseConnection.getConnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from student;");
			
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
	
	//Delete D
	public boolean deleteStudent(Long studentId) {
		boolean deletedOk = false;
		connection = DataBaseConnection.getConnection();
		try {
			pStmt = connection.prepareStatement("delete from student where id=?;");
			pStmt.setLong(1,studentId);
			
			int rowEffected = pStmt.executeUpdate();
			if(rowEffected > 0) deletedOk = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return deletedOk;
	}

}
