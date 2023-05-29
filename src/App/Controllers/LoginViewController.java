package App.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import App.MainApp;
import App.Model.Cliente;
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
    @FXML private Button btnRegistro;
    
    @FXML private AnchorPane panelRegistrarUsuario;
    @FXML private TextField txtNuevoNombre;
    @FXML private TextField txtNuevoApellido;
    @FXML private TextField txtNuevoEmail;
    @FXML private TextField txtNuevoCodigoAfiliado;
    @FXML private TextField txtNuevoTelefono;
    @FXML private TextField txtNuevoDireccion;
    @FXML private TextField txtNuevaContrasenia;
    @FXML private TextField txtConfirmarNuevaContrasenia;
    @FXML private Button btnCrearCuenta;
    @FXML private Button btnRegresar;
  
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
    void onBtnIngresar(ActionEvent event) {
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
				
				Cliente usuarioLogeado = modelFactoryController.getClientePorUsuarioYContrasenia(usuario, contrasenia);				
				mainApp.setUsuarioLogeado(usuarioLogeado);
				
				// Llamo a TiendaViewController y cambio la view (el fxml)				
				try {			
					FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("Views/Tienda.fxml"));
					Parent root = loader.load();

					// Creo el controlador y se le asigna la MainApp
					TiendaViewController tiendaViewController = loader.getController();
					tiendaViewController.setMainApp(mainApp);
					
					Scene scene = new Scene(root);
					mainApp.getPrimaryStage().setScene(scene);					
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
								
			}
			else{
				mostrarMensaje("Notifacion", "Login Incorrecto", "Usuario NO Encontado o la Contrasenia es Incorrecta. Puede que la cuenta este desactivada", AlertType.ERROR);
			}
		}
		else{
			mostrarMensaje("Notifacion", "Usuario NO Encontado", "Datos ingresados NO validos", AlertType.ERROR);
		}
    
    }
    
    
    @FXML
    void onBtnRegistro(ActionEvent event) {
    	panelRegistrarUsuario.setVisible(true);
    }

    @FXML
    void onBtnRegresar(ActionEvent event) {
    	panelRegistrarUsuario.setVisible(false);
    }

    
    @FXML
    void onBtnCrearCuenta(ActionEvent event) {
    	crearNuevoUsuario();
    }
    
    private void crearNuevoUsuario() {
		// Captura los datos
    	String nuevoNombre = txtNuevoNombre.getText();    	
        String nuevoApellido = txtNuevoApellido.getText();
        String nuevoEmail = txtNuevoEmail.getText();
        String nuevoCodigoAfiliado = txtNuevoCodigoAfiliado.getText();
        String nuevoTelefono = txtNuevoTelefono.getText();
        String nuevoDireccion = txtNuevoDireccion.getText();
		String nuevaContrasenia = txtNuevaContrasenia.getText();
		String confirmarNuevaContrasenia = txtConfirmarNuevaContrasenia.getText();		
			
		// Valida los datos
		if(datosValidos(nuevoNombre, nuevoApellido, nuevoEmail, nuevoTelefono, nuevoDireccion, nuevaContrasenia, confirmarNuevaContrasenia) 
				&& validarCodigoAfiliado(nuevoCodigoAfiliado)){
			boolean clienteCreado = false;
			
			// Crea el cliente
			clienteCreado = modelFactoryController.crearCliente(nuevoNombre, nuevoApellido, nuevoEmail, nuevoTelefono, nuevoDireccion, nuevaContrasenia, nuevoCodigoAfiliado);		
			
			if(clienteCreado){				
				mostrarMensaje("Notifacion", "Usuario Creado", "Usuario creado con exito! Bienvenido "+ 
								nuevoNombre+ "!", AlertType.INFORMATION);							
				
				// Limpio los textfield
				txtNuevoNombre.clear();    	
		        txtNuevoApellido.clear();
		        txtNuevoEmail.clear();
		        txtNuevoCodigoAfiliado.clear();
		        txtNuevoTelefono.clear();
		        txtNuevoDireccion.clear();
				txtNuevaContrasenia.clear();
				txtConfirmarNuevaContrasenia.clear();
			}
			else{
				mostrarMensaje("Notifacion", "Usuario NO Creado", "El Usuario NO ha sido creado", AlertType.ERROR);
			}
		}
		else{
			mostrarMensaje("Notifacion", "Usuario NO Creado", "Datos ingresados NO validos", AlertType.ERROR);
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
    private boolean datosValidos(String nuevoNombre, String nuevoApellido, String nuevoEmail,
    		String nuevoTelefono, String nuevoDireccion, String nuevaContrasenia, String confirmarNuevaContrasenia){
    	String mensaje = "";
    	
    	if(nuevoNombre == null || nuevoNombre.equals(""))
    		mensaje += "Nombre no valido\n";
    	
    	if(nuevoApellido == null || nuevoApellido.equals(""))
    		mensaje += "Apellidos no validos\n";
    	
    	if(nuevoEmail == null || nuevoEmail.equals(""))
    		mensaje += "Email no valido\n";
    	
    	if(nuevoTelefono == null || nuevoTelefono.equals(""))
    		mensaje += "Telefono no valido\n";
    	
    	// Tambien se debe verificar si el numeroTelefono es un numero (aunque lo manejemos con string aquí y en la BD) 
    	try { 
            Integer.parseInt(nuevoTelefono); 
        } catch(NumberFormatException e) { 
        	mensaje += "Telefono no valido\n"; 
        } catch(NullPointerException e) {
        	mensaje += "Telefono no valido\n";
        }
    	
    	// La direccion puede ser nula
//    	if(nuevoDireccion == null || nuevoDireccion.equals(""))
//    		mensaje += "Direccion no valida\n";
    	
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
    
    
    private boolean validarCodigoAfiliado(String nuevoCodigoAfiliado) {
    	String mensaje = "";
    	
    	// Verifica si el campo codigo está vacio.
    	// Retorna directamente true porque no es obligatorio poner un codigoafiliado
    	if(nuevoCodigoAfiliado == null || nuevoCodigoAfiliado.equals("")) {    	
    		return true;
    	}
    		
    	// Si el usuario puso un codigo es necesario verificarlo
    	// Se verifica si el codigo es un numero (no letras) y se verifica 
    	// si el codigo es de algun cliente en la BD
    	try { 
            Integer codigo = Integer.parseInt(nuevoCodigoAfiliado);
            
            if(!modelFactoryController.verificarCodigoAfiliado(codigo))
        		mensaje += "Codigo de Afiliacion no valido\n";
        } catch(NumberFormatException e) { 
        	mensaje += "Codigo de Afiliacion no valido\n"; 
        } catch(NullPointerException e) {
        	mensaje += "Codigo de Afiliacion no valido\n";
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
