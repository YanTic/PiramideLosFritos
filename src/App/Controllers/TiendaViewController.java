package App.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import App.MainApp;
import App.Controllers.dinamico.ProductoController;
import App.Model.Cliente;
import App.Model.Producto;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TiendaViewController implements Initializable {

	@FXML private TabPane mainTabPane;    
	@FXML private Pane paneCarrito;
    @FXML private Button btnLogout;
    @FXML private Button btnUser;  
    @FXML private Button btnCarrito;
    @FXML private Button btnComprar;
    @FXML private Button btnEliminarProductos;
    @FXML private Button btnSalirCarrito;
    
    @FXML private GridPane gridPaneProductos;
    @FXML private GridPane gridPaneCarrito;
     
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
		ArrayList<Producto> productos = modelFactoryController.getProductos();
		
		// Recordar: Para proyectos con gridpanes dinamica, en el scenebuilder, siempre
		// dejar el gridpane sin filas ni columnas (bueno, una si). De tal manera
		// que quede en el SceneBuilder como: GridPane (1 x 0).
		// Esto se hace porque ya le estamos agregando la cantidad de elementos que
		// va a tener, cuantas filas y columnas en esta funcion, y ya el tamaño es
		// segun el AnchorPane de la vista dinamica que creamos (proyecto.fxml) 
		gridPaneProductos.getChildren().clear();
		int fila = 0;	
		
		for(int i = 0; i<productos.size(); i++) {
			try{
				// Cargamos la vista dinamica
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(MainApp.class.getResource("Views/dinamico/producto.fxml"));
				
				// Agregamos la vista al gridpane de proyecto
				gridPaneProductos.add(fxmlLoader.load(), 0, fila);
				
				// No se usan columnas, porque solo será una, solo aumentarán las filas
				fila++;
				
				// Cargamos el controlador de la vista
				// El controlador solo se crea despues de cargar el fxml 
				// usando 'fxmlLoader.load()'
				ProductoController productoController = fxmlLoader.getController();
				productoController.establecerDatos(mainApp, productos.get(i));				
			}
			catch (IOException e) {
				e.printStackTrace();
			}			
		}		
	}
	
	
	private void inicializarCarrito() {
		// Se debe volver a actualizar la instancia del ModelFactory, porque el controlador
		// de cada producto agrega valores nuevos a este ModelFactory
		modelFactoryController =  mainApp.getModelFactoryController();
		
		ArrayList<Producto> productos = modelFactoryController.getProductosCarrito();
		
		gridPaneCarrito.getChildren().clear();
		int fila = 0;	
		
		for(int i = 0; i<productos.size(); i++) {
			AnchorPane ap = new AnchorPane();
			ap.setMaxWidth(600);
			ap.setMaxHeight(25);
			Label p = new Label();
			p.maxWidth(600);		
			p.setText(productos.get(i).getNombre()+" |  $"+productos.get(i).getPrecio());
			ap.getChildren().add(p);
			
			// Agregamos la vista al gridpane de proyecto
			gridPaneCarrito.add(ap, 0, fila);
			
			// No se usan columnas, porque solo será una, solo aumentarán las filas
			fila++;				
		}
		
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
    	cerrarSesion();
    }
    
    public void cerrarSesion() { 
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
    	inicializarCarrito();
    	paneCarrito.setVisible(true);
    }
    
    @FXML
    void onBtnEliminarProductos(ActionEvent event) {
    	if(verificar("Confirmacion", "Eliminar Carrito", "¿Esta seguro de eliminar los productos de su carrito de compra?")) {
    		mainApp.getModelFactoryController().getProductosCarrito().clear();
    		inicializarCarrito();	
    	}
    }

    @FXML
    void onBtnSalirCarrito(ActionEvent event) {
    	paneCarrito.setVisible(false);
    }
    
    @FXML
    void onBtnComprar(ActionEvent event) {
    	if(verificar("Confirmacion", "Realizar Compra", "¿Esta seguro de realizar la compra?")) {
    		if(modelFactoryController.comprar(mainApp.getModelFactoryController().getProductosCarrito(), mainApp.getUsuarioLogeado())){
    			mostrarMensaje("Notifacion", "Compra Realizada", "La compra se ha realizado correctamente", AlertType.INFORMATION);
    			mainApp.getModelFactoryController().getProductosCarrito().clear();
        		inicializarCarrito();	    			
    		}
        	else
        		mostrarMensaje("Notifacion", "Compra NO Realizada", "La compra no se realizó con exito", AlertType.ERROR);	
    	}
    	
    }
    
    @FXML
    void onBtnDesactivarCuenta(ActionEvent event) {
    	if(verificar("Confirmacion", "Desactivar Cuenta", "¿Esta seguro que quiere desactivar su cuenta?")) {
    		if(modelFactoryController.desactivarCuenta(mainApp.getUsuarioLogeado())){
    			mostrarMensaje("Notifacion", "Cuenta Desactivada", "Su cuenta ha sido desactivada correctamente. Regresando a la pagina principal...", AlertType.INFORMATION);
    			cerrarSesion();    			
    		}    			
        	else
        		mostrarMensaje("Notifacion", "Compra NO Desactivada", "Su cuentano se pudo desactivar", AlertType.ERROR);	
    	}    	
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

    public boolean verificar(String title, String header, String text) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle(title);
    	alert.setHeaderText(header);  	
    	alert.setContentText(text);
    	
    	Optional<ButtonType> resultado = alert.showAndWait();
    	if(resultado.isPresent() && resultado.get() == ButtonType.OK)
    		return true;
    	else
    		return false;
    }

}