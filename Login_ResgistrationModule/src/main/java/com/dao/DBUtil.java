package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	static Connection con;
	public static Connection getConnection()
	{
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String username = "root";
			String password = "root";
			String url = "jdbc:mysql://localhost:3306/student_manage";
			con = DriverManager.getConnection(url,username,password);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return con;
	}
}
