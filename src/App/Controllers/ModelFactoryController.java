package App.Controllers;

import java.util.ArrayList;

import App.Model.Cliente;
import App.Model.Producto;
import App.Service.DatabaseService;

public class ModelFactoryController {

	DatabaseService databaseService;
	ArrayList<Producto> productosCarrito;
	
	
	//------------------------------  Singleton ------------------------------------------------
	// Clase estatica oculta. Tan solo se instanciara el singleton una vez
	private static class SingletonHolder {
		// El constructor de Singleton puede ser llamado desde aquí al ser protected
		private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
	}

	// Método para obtener la instancia de nuestra clase
	public static ModelFactoryController getInstance() {
		return SingletonHolder.eINSTANCE;
	}
		
	public ModelFactoryController() {			
		inicializarDatos();			
	}
		
	
	private void inicializarDatos() {
		databaseService = new DatabaseService();
		productosCarrito = new ArrayList<>();
	}
	
	
	public boolean verificarUsuario(String usuario, String contrasenia) {		
		return databaseService.verificarUsuario(usuario, contrasenia);
	}
	
	public boolean verificarCodigoAfiliado(Integer codigoAfiliado) {
		return databaseService.verificarCodigoAfiliado(codigoAfiliado);
	}

	public Cliente getClientePorUsuarioYContrasenia(String usuario, String contrasenia) {
		return databaseService.getClientePorUsuarioYContrasenia(usuario, contrasenia);
	}
	
	public ArrayList<Producto> getProductos() {		
		return databaseService.getProductos();
	}

	public boolean crearCliente(String nombre, String apellido, String email, String telefono,
			String direccion, String contrasenia, String codigoAfiliado) { 
		boolean clienteCreado = false;
		Cliente nuevoCliente = new Cliente();
		nuevoCliente.setNombre(nombre);
		nuevoCliente.setApellido(apellido);
		nuevoCliente.setEmail(email);
		nuevoCliente.setTelefono(telefono);
		nuevoCliente.setDireccion(direccion);
		nuevoCliente.setMembresia("FRITO MADERA"); // Primer nivel cuando se crea un cuenta. Esto lo hace un Trigger cuando se inserta en la tabla Cliente
		nuevoCliente.setAfiliado_id(Integer.parseInt(codigoAfiliado));		
		
		clienteCreado = databaseService.crearCliente(nuevoCliente, contrasenia);
		
		return clienteCreado;
	}

	public ArrayList<Producto> getProductosCarrito() {
		return productosCarrito;
	}

	public boolean comprar(ArrayList<Producto> productosCarrito2) {
		return databaseService.comprar(productosCarrito2);
	}

	public boolean desactivarCuenta(Cliente cliente) {
		return databaseService.desactivarCuenta(cliente);
	}
}
