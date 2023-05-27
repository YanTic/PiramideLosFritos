package App.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import App.Model.Domain;

public class ModelFactoryController {

	Domain domain;
	
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
		
		// Creacion de personas
//		Persona persona = new Persona();
//		persona.setId(1);
//		persona.setNombre("Ana");
//		persona.setApellido("Rodriguez");
//		persona.setContrasenia("ana123");
//		persona.setEmail("ana@email.com");
//		persona.setFechaNacimiento(LocalDate.of(1995, 12, 31));
//		persona.setDocumentos(null);
//			ArrayList<Mensaje> msgs = new ArrayList<>();
//			msgs.add(new Mensaje("Holaa"));
//		persona.setMensajes(msgs);
//		domain.getListaPersonas().add(persona);
				
	}
	
	//TODO: ESTO DEBE SER LLAMANDO A LA BASE DE DATOS
	
//	public boolean verificarUsuario(String usuario, String contrasenia) {		
//		return domain.verificarUsuario(usuario, contrasenia);
//	}
//	
	
	
//	public Persona getPersonaPorId(int idPersona) {
//		return domain.getPersonaPorId(idPersona);
//	}
//	
//	public Persona getPersonaPorUsuarioYContrasenia(String user, String contrasenia) {
//		return domain.getPersonaPorUsuarioYContrasenia(user, contrasenia);
//	}
	

}
