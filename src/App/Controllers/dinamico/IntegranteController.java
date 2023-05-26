package App.Controllers.dinamico;

import java.net.URL;
import java.util.ResourceBundle;

import App.MainApp;
import App.Model.Persona;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class IntegranteController implements Initializable{
	
    @FXML private AnchorPane anchorPane;
    @FXML private Label nombreIntegrante;
    @FXML private Label correoIntegrante;
    @FXML private Button btnSaludarIntegrante;

    MainApp mainApp;
	Persona persona;
    
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
	public void establecerDatos(MainApp mainApp, Persona persona) {
		this.mainApp = mainApp;
		this.persona = persona;
	}
	
	public void inicializarDatos() {
		nombreIntegrante.setText(persona.getNombre()+" "+persona.getApellido());
	    correoIntegrante.setText(persona.getEmail());	
	}
	
    @FXML
    void onBtnSaludarIntegrante(ActionEvent event) {

    }    
	
    public void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType){
    	Alert alert = new Alert(alertType);
    	alert.setTitle(titulo);
    	alert.setHeaderText(header);
    	alert.setContentText(contenido);
    	alert.showAndWait();
    }

}
