package App.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import App.Model.Cliente;

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

	public boolean verificarUsuario(String usuario, String contrasenia) {
		boolean esUsuarioExistente = false;
		
		try {
			statement = connection.createStatement();
			String sql = "SELECT ID FROM CLIENTE "+
						 "WHERE NOMBRE = \'"+usuario+"\' AND CONTRASENIA = \'"+contrasenia+"\'";
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				System.out.println("id user: "+ resultSet.getInt("ID"));
				
				if(resultSet.getInt("ID") != 0)
					esUsuarioExistente = true;
			}			
			System.out.println("Terminado");
			
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return esUsuarioExistente;
	}

	public Cliente getClientePorUsuarioYContrasenia(String usuario, String contrasenia) {
		Cliente cliente = new Cliente();
		Integer membresia_id = 0; // No se puede obtener el nombre por lo que se guarda el id para buscarlo en la tabla mediante otra consulta
		
		try {
			statement = connection.createStatement();
			String sql = "SELECT * FROM CLIENTE "+
						 "WHERE NOMBRE = \'"+usuario+"\' AND CONTRASENIA = \'"+contrasenia+"\'";			
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {			
				cliente.setNombre(resultSet.getString("NOMBRE"));
				cliente.setApellido(resultSet.getString("APELLIDO"));
				cliente.setEmail(resultSet.getString("EMAIL"));
				cliente.setTelefono(resultSet.getString("TELEFONO"));
				cliente.setDireccion(resultSet.getString("DIRECCION"));				
				cliente.setAfiliado_id(resultSet.getInt("AFILIADO_ID"));
				
				membresia_id = resultSet.getInt("NIVELMEMBRESIA_ID");
			}						
			
// Ya no se puede obtener la ciudad, por problemas de TIEMPO, cambiar todo el view
// y agregar comboboxs y demas cosas para usar la ciudad, es muy complicado y tardado.			
			// Se obtiene el nombre de la ciudad con otra consulta
//			statement = connection.createStatement();
//			sql = "SELECT NOMBRE FROM CIUDAD WHERE ID = "+ciudad_id;						 			
//			resultSet = statement.executeQuery(sql);
//			
//			while(resultSet.next()) {			
//				cliente.setCiudad(resultSet.getString("NOMBRE"));
//			}

			// Se obtiene el nombre de la membresia con otra consulta
			statement = connection.createStatement();
			sql = "SELECT NOMBRE FROM MEMBRESIA WHERE ID = "+membresia_id;								
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {			
				cliente.setMembresia(resultSet.getString("NOMBRE"));
			}
			
			System.out.println(""+cliente.toString());
		} 
		catch (SQLException e) {e.printStackTrace();}		
		
		
		return cliente;
	}

	public boolean verificarCodigoAfiliado(Integer codigoAfiliado) {
		boolean esAfiliadorExistente = false;
		
		try {
			statement = connection.createStatement();
			String sql = "SELECT ID FROM CLIENTE WHERE ID = \'"+codigoAfiliado+"\'";
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				System.out.println("afiliador: "+ resultSet.getInt("ID"));				
				if(resultSet.getInt("ID") == codigoAfiliado)
					esAfiliadorExistente = true;
			}
			System.out.println("Terminado");			
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return esAfiliadorExistente;
	}
	
	
	public boolean crearCliente(Cliente nuevoCliente, String contrasenia) {
		boolean esClienteCreado = false;
		
		try {
			statement = connection.createStatement();
			
			// La membresia se pone la ultima '4':"FRITO MADERA" (membresia por defecto). Aunque debe 
			// haber un trigger que verifique esto, cada vez que se inserta en la tabla Cliente
			// Como ciudad no se está utilizando en el frontend, mandamos una ciudad cualquiera
			String sql = "Insert into CLIENTE (ID,NIVELMEMBRESIA_ID,CIUDAD_ID,AFILIADO_ID,NOMBRE,APELLIDO,EMAIL,CONTRASENIA,TELEFONO,DIRECCION,ESTADO) "+
					"values ('"+(getUltimoIdCliente()+1)+"','4','1',"+
					"'"+nuevoCliente.getAfiliado_id()+"','"+nuevoCliente.getNombre()+"',"+
					"'"+nuevoCliente.getApellido()+"','"+nuevoCliente.getEmail()+"',"+
					"'"+contrasenia+"','"+nuevoCliente.getTelefono()+"','"+nuevoCliente.getDireccion()+"',1)";
			
			statement.executeQuery(sql);	
			System.out.println("Cliente insertado");
			esClienteCreado = true;
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return esClienteCreado;
	}
	
	
	public Integer getUltimoIdCliente(){
		Integer ultimoIdCliente = 0;
		try {
			statement = connection.createStatement();				
			String sql = "SELECT MAX(ID) codigo FROM CLIENTE";			
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {				
				ultimoIdCliente = resultSet.getInt("codigo");
			}			
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return ultimoIdCliente;
	}
	
}
