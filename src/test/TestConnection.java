package test;

import java.sql.Connection;

import database.DataBaseConnection;

public class TestConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = DataBaseConnection.getConnection();
		if(connection != null) {
			System.out.println("Successfully Connected to DB");
		}else {
			System.out.println("Database Connection failed!");
		}
	}

}
