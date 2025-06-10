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
			String password = "CAjtxaLruzudgLbQOIGIZJIEdBxyFLoR";
			String url = "jdbc:mysql://mysql.railway.internal:3306/railway";
			con = DriverManager.getConnection(url,username,password);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return con;
	}
}
