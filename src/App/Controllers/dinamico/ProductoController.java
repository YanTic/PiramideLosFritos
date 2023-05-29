package App.Controllers.dinamico;

import java.net.URL;
import java.util.ResourceBundle;

import App.MainApp;
import App.Model.Producto;
import javafx.application.Platform;
import javafx.fxml.Initializable;

public class ProductoController implements Initializable {

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
//		nombreTarea.setText(tarea.getNombre());
//	    descTarea.setText(tarea.getDescripción());
//	    idTarea.setText(tarea.getId());
//	    fechaCreacionTarea.setText(tarea.getFechaCreacion().toString());
//	    prioridadTarea.setText(""+tarea.getPrioridad());
//	    estadoTarea.setText(tarea.getEstado().toString());
//	    estadoTarea1.setText(tarea.getEstado().toString());
//
//	    cbEstadoTarea.getItems().clear();
//		cbEstadoTarea.getItems().addAll(EstadoTarea.values());
//		cbEstadoTarea.getSelectionModel().clearSelection();		
	}

}
