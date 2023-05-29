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
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class TiendaViewController implements Initializable {

	@FXML private TabPane mainTabPane;    
    @FXML private Button btnLogout;
    @FXML private Button btnUser;  
    @FXML private Button btnCarrito;
    @FXML private GridPane gridPaneProductos;
    
    @FXML private Button btnGuardarCambios;
    @FXML private Button btnDesactivarCuenta;
    @FXML private TextField txtNombreCliente;
    @FXML private TextField txtApellidoCliente;
    @FXML private TextField txtEmailCliente;
    @FXML private TextField txtTelefonoCliente;
    @FXML private TextField txtDireccionCliente;
    @FXML private Label lbMembresia;

    private MainApp mainApp;
    ModelFactoryController modelFactoryController;
//    Proyecto proyectoAsignado; 
//    Tarea tareaSeleccionada;
//    Persona usuario;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(()->{
//			No se obtiene la instancia de la clase, sino de la MainApp
//			la cual ya instanció el ModelFactory: 
//			modelFactoryController = ModelFactoryController.getInstance();
			modelFactoryController =  mainApp.getModelFactoryController();
			inicializarVista();
		});				
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;		
	}
   
	public void inicializarVista() {
		btnUser.setText(mainApp.getUsuarioLogeado().getNombre());		
		inicializarProductos();
		inicializarDatosCliente();
	}
	
	private void inicializarProductos() {
//		ArrayList<Proyecto> proyectos = modelFactoryController.getProyectosPorPersona(mainApp.getUsuarioLogeado().getId());
//		lb_Equipo_Persona.setText("Equipo #"+modelFactoryController.getEquipoPorPersona(mainApp.getUsuarioLogeado().getId()).getId());
		
		// Recordar: Para proyectos con gridpanes dinamica, en el scenebuilder, siempre
		// dejar el gridpane sin filas ni columnas (bueno, una si). De tal manera
		// que quede en el SceneBuilder como: GridPane (1 x 0).
		// Esto se hace porque ya le estamos agregando la cantidad de elementos que
		// va a tener, cuantas filas y columnas en esta funcion, y ya el tamaño es
		// segun el AnchorPane de la vista dinamica que creamos (proyecto.fxml) 
//		gridPaneProyectos.getChildren().clear();
//		int fila = 0;
//		int columna = 0;		
//		
//		for(int i = 0; i<proyectos.size(); i++) {
//			try{
//				// Cargamos la vista dinamica
//				FXMLLoader fxmlLoader = new FXMLLoader();
//				fxmlLoader.setLocation(MainApp.class.getResource("Views/dinamico/proyecto.fxml"));
//												
//				// Por estetica, solo se va a permitir ver dos proyectos por fila 
//				// Luego de haber dos en la fila, creamos otra fila.
//				if(columna == 2) {
//					columna = 0;
//					fila++;
//				}
//				
//				// Agregamos la vista al gridpane de proyecto
//				gridPaneProyectos.add(fxmlLoader.load(), ++columna, fila);
//				
//				// Cargamos el controlador de la vista
//				// El controlador solo se crea despues de cargar el fxml 
//				// usando 'fxmlLoader.load()'
//				ProyectoController proyectoController = fxmlLoader.getController();
//				proyectoController.establecerDatos(proyectos.get(i), mainApp);
//				
//			}
//			catch (IOException e) {
//				e.printStackTrace();
//			}			
//		}
		
	}
	
	private void inicializarDatosCliente() {
		Cliente c = mainApp.getUsuarioLogeado(); 
		
	    txtNombreCliente.setText(c.getNombre());
	    txtApellidoCliente.setText(c.getApellido());
	    txtEmailCliente.setText(c.getEmail());
	    txtTelefonoCliente.setText(c.getTelefono());
	    txtDireccionCliente.setText(c.getDireccion());
	    lbMembresia.setText(c.getMembresia());
	}
	  
    @FXML
    void onBtnLogout(ActionEvent event) { 
			mostrarMensaje("Notifacion", "Cerrando Sesion", "Usuario: "+mainApp.getUsuarioLogeado().getNombre()+ " ha cerrado sesion", AlertType.INFORMATION);
			mainApp.setUsuarioLogeado(null);
			
			// Llamo al LoginViewController y cambio la view (el fxml)				
			try {			
				FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("Views/Login.fxml"));
				Parent root = loader.load();
				
				// Creo el controlador
				LoginViewController loginViewController = loader.getController();
				loginViewController.setMainApp(mainApp);
			
				Scene scene = new Scene(root);
				mainApp.getPrimaryStage().setScene(scene);				
			} 
			catch (IOException e) {
				e.printStackTrace();
    		}	
    }
    
    
    @FXML
    void onBtnCarrito(ActionEvent event) {

    }
    
    @FXML
    void onBtnDesactivarCuenta(ActionEvent event) {

    }

    @FXML
    void onBtnGuardarCambios(ActionEvent event) {

    }
    
    public void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType){
    	Alert alert = new Alert(alertType);
    	alert.setTitle(titulo);
    	alert.setHeaderText(header);
    	alert.setContentText(contenido);
    	alert.showAndWait();
    }


}