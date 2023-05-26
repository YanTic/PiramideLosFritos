package App.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import App.MainApp;
import App.Model.Persona;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginViewController implements Initializable {

	@FXML private AnchorPane panelIniciarSesion;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtContrasenia;
    @FXML private Button btnIngresar;
    @FXML private Button btnAyuda;

	private MainApp mainApp;
    ModelFactoryController modelFactoryController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(()->{
//			No se obtiene la instancia de la clase, sino de la MainApp
//			la cual ya instanció el ModelFactory: 
//			modelFactoryController = ModelFactoryController.getInstance();
			modelFactoryController =  mainApp.getModelFactoryController();
		});				
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;		
	}
	
	
    @FXML
    void accionBtnAyuda(ActionEvent event) {

    }

    @FXML
    void accionBtnIngresar(ActionEvent event) {
    	inicioSesion();
    }
    
    public void inicioSesion(){
    	// Captura los datos
		String usuario = txtUsuario.getText();
		String contrasenia = txtContrasenia.getText();
		
		// Valida los datos
		if(datosValidos(usuario, contrasenia)){
			// Comparo los datos del usuario 
			if(modelFactoryController.verificarUsuario(usuario, contrasenia)){
				mostrarMensaje("Notifacion", "Login Correcto", "Bienvenido "+usuario+ "!", AlertType.INFORMATION);				
				
				Persona usuarioLogeado = modelFactoryController.getPersonaPorUsuarioYContrasenia(usuario, contrasenia);				
				mainApp.setUsuarioLogeado(usuarioLogeado);
				
				// Llamo a ColaboracionViewController y cambio la view (el fxml)				
				try {			
					FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("Views/Colaboracion.fxml"));
					Parent root = loader.load();

					// Creo el controlador y se le asigna la MainApp
					ColaboracionViewController colaboracionViewController = loader.getController();
					colaboracionViewController.setMainApp(mainApp);
					
					Scene scene = new Scene(root);
					mainApp.getPrimaryStage().setScene(scene);					
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
								
			}
			else{
				mostrarMensaje("Notifacion", "Usuario NO Encontado o Contrasenia es Incorrecta", "Datos ingresados NO validos", AlertType.ERROR);
			}
		}
		else{
			mostrarMensaje("Notifacion", "Usuario NO Encontado", "Datos ingresados NO validos", AlertType.ERROR);
		}
    
    }
    
    public void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType){
    	Alert alert = new Alert(alertType);
    	alert.setTitle(titulo);
    	alert.setHeaderText(header);
    	alert.setContentText(contenido);
    	alert.showAndWait();
    }
    
    private boolean datosValidos(String usuario, String contrasenia){
    	String mensaje = "";
    	
    	if(usuario == null || usuario.equals(""))
    		mensaje += "Usuario no valido\n";
    	
    	if(contrasenia == null || contrasenia.equals(""))
    		mensaje += "Contrasenia no valida\n";
    	
    	if(mensaje.equals("")){
    		return true;    		
    	}
    	else{
    		mostrarMensaje("Notificacion", "Datos no validos", mensaje, AlertType.WARNING);
    		return false;    		
    	}
    	
    } 
    
    /*
     * Este metodo valida los datos de un --- Registro de Usuario ---
     * */
    private boolean datosValidos(String nuevoUsuario, String nuevaContrasenia, String confirmarNuevaContrasenia){
    	String mensaje = "";
    	
    	if(nuevoUsuario == null || nuevoUsuario.equals(""))
    		mensaje += "Usuario no valido\n";
    	
    	if(nuevaContrasenia == null || nuevaContrasenia.equals(""))
    		mensaje += "Contrasenia no valida\n";
    	
    	if(confirmarNuevaContrasenia == null || confirmarNuevaContrasenia.equals(""))
    		mensaje += "Contrasenia no valida\n";
    	
    	// Verifico si las dos contraseñas No son iguales
    	if(!nuevaContrasenia.equals(confirmarNuevaContrasenia)){
    		mensaje += "Las contraseñas no son iguales\n"; 
    	}
    	
    	if(mensaje.equals("")){
    		return true;    		
    	}
    	else{
    		mostrarMensaje("Notificacion", "Datos no validos", mensaje, AlertType.WARNING);
    		return false;    		
    	}
    	
    } 
}
