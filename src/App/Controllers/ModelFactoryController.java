package App.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import App.Model.Cliente;
import App.Model.Domain;
import App.Service.DatabaseService;

public class ModelFactoryController {

	Domain domain;
	DatabaseService databaseService;
	
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
		if(domain == null){
			System.out.println("DOMAIN ES NULL");
			
			inicializarDatos();			
		}
	}
		
	
	private void inicializarDatos() {
		domain = new Domain();		
		databaseService = new DatabaseService();
	}
	
	
	public boolean verificarUsuario(String usuario, String contrasenia) {		
		return databaseService.verificarUsuario(usuario, contrasenia);
	}

	public Cliente getClientePorUsuarioYContrasenia(String usuario, String contrasenia) {
		return databaseService.getClientePorUsuarioYContrasenia(usuario, contrasenia);
	}
	
	
	
//	public Persona getPersonaPorId(int idPersona) {
//		return domain.getPersonaPorId(idPersona);
//	}
//	
//	public Persona getPersonaPorUsuarioYContrasenia(String user, String contrasenia) {
//		return domain.getPersonaPorUsuarioYContrasenia(user, contrasenia);
//	}
	

}
