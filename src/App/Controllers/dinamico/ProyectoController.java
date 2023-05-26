package App.Controllers.dinamico;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import App.MainApp;
import App.Controllers.ColaboracionViewController;
import App.Controllers.InfoProyectoViewController;
import App.Model.Proyecto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ProyectoController implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private Label idProyecto;
    @FXML private Label nombreProyecto;
    @FXML private Label estadoProyecto;
    @FXML private Button btnVerProyecto;
    
	Proyecto proyecto;
	MainApp mainApp;
    
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
	// datos en una variable normal (proyecto) la cual se le obtendran los datos
	// para luego asignarlos a los atributos @FXML usando Platform.runLater()
	
	// Platform.runLater() --> se Ejecuta luego de crear todos los atributos
	// del controlador de javafx (@FXML en concreto)
	public void establecerDatos(Proyecto proyecto, MainApp mainApp) {
		this.proyecto = proyecto;		
		this.mainApp = mainApp;
	}
	
	public void inicializarDatos() {
		idProyecto.setText(""+proyecto.getId());
	    nombreProyecto.setText(proyecto.getNombre());
	    estadoProyecto.setText(proyecto.getEstado().toString());
	}
	
	// Este boton cargará otra vista, en la que se muestre toda la informacion de
	// un proyecto, tareas y demas
	@FXML
    void onBtnVerProyecto(ActionEvent event) {
		try {			
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("Views/InfoProyecto.fxml"));
			Parent root = loader.load();

			// Creo el controlador y se le asigna la MainApp
			InfoProyectoViewController infoProyectoViewController = loader.getController();
			infoProyectoViewController.setMainAppYProyecto(mainApp, proyecto);
			
			Scene scene = new Scene(root);
			mainApp.getPrimaryStage().setScene(scene);					
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

}
