package App.Controllers.dinamico;

import java.net.URL;
import java.util.ResourceBundle;

import App.MainApp;
import App.Model.Producto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ProductoController implements Initializable {

	@FXML private AnchorPane anchorPane;
    @FXML private Label nombreProducto;
    @FXML private Label descProducto;
    @FXML private Label unidadesDisponibles;
    @FXML private Label categoriaProducto;
    @FXML private Label precioProducto;
    @FXML private Button btnAgregarCarrito;

	MainApp mainApp;
	Producto producto;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(()->{
			inicializarDatos();
		});
	}
	
	// Recordar: Javafx funciona de manera extraña, como cualquier otro framework.
	// Cuando llamamos esta funcion luego de crear un controlador con esta clase,
	// No podemos asignarle valores a sus atributos @FXML (label, button ...), 
	// porque aún no se han creado por completo. Por lo que hacemos es guardar
	// datos en una variable normal (producto) la cual se le obtendran los datos
	// para luego asignarlos a los atributos @FXML usando Platform.runLater()
	
	// Platform.runLater() --> se Ejecuta luego de crear todos los atributos
	// del controlador de javafx (@FXML en concreto)
	public void establecerDatos(MainApp mainApp, Producto producto) {
		this.mainApp = mainApp;
		this.producto = producto;		
	}
	
	public void inicializarDatos() {
		nombreProducto.setText(producto.getNombre());
	    descProducto.setText(producto.getDescripcion());
	    unidadesDisponibles.setText(producto.getUnidadesDisponibles());
	    categoriaProducto.setText(producto.getCategoria());
	    precioProducto.setText(producto.getPrecio());
	}
	
	@FXML
    void onBtnAgregarCarrito(ActionEvent event) {

    }

}
