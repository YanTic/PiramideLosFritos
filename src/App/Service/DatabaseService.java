package App.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import App.Model.Cliente;
import App.Model.Producto;

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
			System.out.println("Conexi�n Creada");
		} 
		catch (ClassNotFoundException e) {e.printStackTrace();} 
		catch (SQLException e) {e.printStackTrace();}
	}

	public boolean verificarUsuario(String usuario, String contrasenia) {
		boolean esUsuarioExistente = false;
		
		try {
			statement = connection.createStatement();
			String sql = "SELECT ID, ESTADO FROM CLIENTE "+
						 "WHERE NOMBRE = \'"+usuario+"\' AND CONTRASENIA = \'"+contrasenia+"\'";
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				System.out.println("id user: "+ resultSet.getInt("ID"));
				System.out.println("estado user: "+ resultSet.getInt("ESTADO"));
				
				// Si el estado es 0 significa que la cuenta est� desactivada
				if(resultSet.getInt("ID") != 0 && resultSet.getInt("ESTADO") == 1)
					esUsuarioExistente = true;
			}			
			System.out.println("Terminado");
			
		} catch (SQLException e) {e.printStackTrace();}
		
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
			// Como ciudad no se est� utilizando en el frontend, mandamos una ciudad cualquiera
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

	public ArrayList<Producto> getProductos() {
		ArrayList<Producto> productos = new ArrayList<>();
		
		try {
			statement = connection.createStatement();				
			String sql = "SELECT NOMBRE, PRECIO, UNIDADESDISPONIBLES, DESCRIPCION, CATEGORIA_ID FROM PRODUCTO";			
			resultSet = statement.executeQuery(sql);
			
			Producto producto;
			
			while (resultSet.next()) {
				producto = new Producto();
				producto.setNombre(resultSet.getString("NOMBRE"));
				producto.setPrecio(resultSet.getString("PRECIO"));
				producto.setUnidadesDisponibles(resultSet.getString("UNIDADESDISPONIBLES"));
				producto.setDescripcion(resultSet.getString("DESCRIPCION"));
				producto.setCategoria(getNombreCategoria(resultSet.getInt("CATEGORIA_ID")));
				productos.add(producto);				
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		
		return productos;
	}
	
	public String getNombreCategoria(Integer categoria_id) {
		String categoria = null;
		
		try {
			// Se tiene que crear otro statement al global porque esta funcion es llamada
			// dentro de un while() que usa el statement global
			Statement statement = connection.createStatement();
			String sql = "SELECT NOMBRE FROM CATEGORIA WHERE ID = "+categoria_id;								
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {			
				categoria = resultSet.getString("NOMBRE");
			}		
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return categoria;
	}
		
	public Integer getIdCliente(Cliente c) {
		Integer id = null;
		
		try {
			// Se tiene que crear otro statement al global porque esta funcion es llamada
			// dentro de un while() que usa el statement global
			Statement statement = connection.createStatement();
			
			// TODO: La BD se tiene que actualizar y poner EMAIL como valor UNICO
			String sql = "SELECT ID FROM CLIENTE "+
					 "WHERE NOMBRE = '"+c.getNombre()+"' AND APELLIDO = '"+c.getApellido()+
					 "' AND EMAIL = '"+c.getEmail()+"'";								
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {			
				id = resultSet.getInt("ID");
			}		
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return id;
	}
	

	public boolean comprar(ArrayList<Producto> carrito, Cliente c) {
		boolean compraRealizada = false;
		
		Integer idNuevaFactura = getUltimoIdFacturaCompra()+1;
		double descuento = 0; // TODO: Por tiempo no se va a utlizar, todo hagalo en el future
		double precioTotal = 0;
		
		for(Producto p : carrito) {
			precioTotal += Double.parseDouble(p.getPrecio());
		}
		
		LocalDate fecha = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YY");
		System.out.println(dtf.format(fecha));
		
		try {
			statement = connection.createStatement();
			
			String sql = "Insert into FACTURACOMPRA (ID,CLIENTE_ID,FECHA,PRECIO,DESCUENTO) "+
					"values ('"+idNuevaFactura+"','"+getIdCliente(c)+
					"',to_date('"+dtf.format(fecha)+"','DD/MM/RR'),'"+precioTotal+"','"+descuento+"')";
			
			statement.executeQuery(sql);	
			System.out.println("Factura Registrada Realizada");
			// El estado de la factura ya lo realiza un TRIGGER []
			
// TODO: Hacer ese TODO, porque sino esto no va a dejar guardar varios productos iguales			
//			for(Producto p : carrito) {
//				statement = connection.createStatement();
//				
//				// TODO: SE DEBE CAMBIAR EL FRONTEND TAMBIEN, PARA QUE NO SE GUARDE
//				// EL REGISTRO DE 1 SOLO PRODUCTO. Agregar como un boton de aumentar
//				// o disminuir la cantidad a comprar.
//				sql = "Insert into DETALLECOMPRA (FACTURACOMPRA_ID,PRODUCTO_ID,CANTIDAD,PRECIOUNITARIO) "+
//				"values ('"+idNuevaFactura+"','"+getIdProducto(p)+"','1','"+p.getPrecio()+"');";
//				
//				statement.executeQuery(sql);	
//			}
//			System.out.println("Detalles Registrados");
			
			
			// Solo guardemos el primero, como prueba pues
			for(Producto p : carrito) {
				statement = connection.createStatement();
				sql = "Insert into DETALLECOMPRA (FACTURACOMPRA_ID,PRODUCTO_ID,CANTIDAD,PRECIOUNITARIO) "+
				"values ('"+idNuevaFactura+"','"+getIdProducto(p)+"','1','"+p.getPrecio()+"')";								
				System.out.println(sql);
				statement.executeQuery(sql);	
				break;
			}
			System.out.println("Detalles Registrados");
			
			
			compraRealizada = true;
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return compraRealizada;
	}
	
	public Integer getUltimoIdFacturaCompra(){
		Integer ultimoIdFactura = 0;
		try {
			statement = connection.createStatement();				
			String sql = "SELECT MAX(ID) codigo FROM FACTURACOMPRA";			
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {				
				ultimoIdFactura = resultSet.getInt("codigo");
			}			
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return ultimoIdFactura;
	}
	
	public Integer getUltimoIdDetalleCompra(){
		Integer ultimoIdDetalle = 0;
		try {
			statement = connection.createStatement();				
			String sql = "SELECT MAX(ID) codigo FROM DETALLECOMPRA";			
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {				
				ultimoIdDetalle = resultSet.getInt("codigo");
			}			
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return ultimoIdDetalle;
	}
	
	public Integer getIdProducto(Producto p){
		Integer idProducto = 0;
		try {
			statement = connection.createStatement();				
			String sql = "SELECT ID FROM PRODUCTO WHERE NOMBRE='"+p.getNombre()+
					"' AND PRECIO='"+p.getPrecio()+"' AND UNIDADESDISPONIBLES='"+
					p.getUnidadesDisponibles()+"' AND DESCRIPCION='"+p.getDescripcion()+"'";			
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {				
				idProducto = resultSet.getInt("ID");
			}			
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return idProducto;
	}
	

	public boolean desactivarCuenta(Cliente cliente) {
		boolean cuentaDesactivada = false;
		Integer id = getIdCliente(cliente);
		
		try {
			statement = connection.createStatement();			
			String sql = "UPDATE CLIENTE SET ESTADO=0 WHERE ID="+id;
			
			statement.executeQuery(sql);	
			System.out.println("Cuenta desactivada");
			cuentaDesactivada = true;
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return cuentaDesactivada;
	}
		
}
