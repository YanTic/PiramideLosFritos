package App.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {
	
	private String jdbc_url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String user = "PROYECTO_BD2";
	private String pwd = "1234";
	
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	public DatabaseService() {
		try {
			//In other words, it allows you to use the Driver class without having an 
			//explicit import for your class. This allows you to build the project without 
			//having to have the Oracle driver in your classpath --StackOverFlow
			Class.forName(driver);
			
			connection = DriverManager.getConnection(jdbc_url,user,pwd);
			System.out.println("Conexión Creada");
		} 
		catch (ClassNotFoundException e) {e.printStackTrace();} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	public void printAllClients() {
		try {
			statement = connection.createStatement();
			String sql = "SELECT * FROM CLIENTE";
			
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				System.out.println("nombre: "+ resultSet.getString("NOMBRE"));				
			}
			
			System.out.println("Terminado");
			
		} 
		catch (SQLException e) {e.printStackTrace();}		
	}
	
}
