package App.Controllers.dinamico;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import App.MainApp;
import App.Model.EstadoTarea;
import App.Model.Proyecto;
import App.Model.Tarea;
import App.Service.EmailService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TareaController implements Initializable {

	@FXML private AnchorPane anchorPane;
    @FXML private Pane paneActualizar;
    
    @FXML private Button btnIrActualizar;
    @FXML private ComboBox<EstadoTarea> cbEstadoTarea;
    @FXML private Button btnActualizarTarea;
    @FXML private Button btnRegresar;
    
    @FXML private Label estadoTarea;
    @FXML private Label estadoTarea1; // Repetido, porque otro pane necesita el mismo label
    @FXML private Label nombreTarea;
    @FXML private Label descTarea;
    @FXML private Label idTarea;
    @FXML private Label fechaCreacionTarea;
    @FXML private Label prioridadTarea;
    
    Proyecto proyecto;
	Tarea tarea;
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
	public void establecerDatos(MainApp mainApp, Proyecto proyecto, Tarea tarea) {
		this.mainApp = mainApp;
		this.proyecto = proyecto;
		this.tarea = tarea;		
	}
	
	public void inicializarDatos() {
		nombreTarea.setText(tarea.getNombre());
	    descTarea.setText(tarea.getDescripción());
	    idTarea.setText(tarea.getId());
	    fechaCreacionTarea.setText(tarea.getFechaCreacion().toString());
	    prioridadTarea.setText(""+tarea.getPrioridad());
	    estadoTarea.setText(tarea.getEstado().toString());
	    estadoTarea1.setText(tarea.getEstado().toString());

	    cbEstadoTarea.getItems().clear();
		cbEstadoTarea.getItems().addAll(EstadoTarea.values());
		cbEstadoTarea.getSelectionModel().clearSelection();		
	}
	
	// Este metodo verifica si se puede actualizar la tarea, y le pide al usuario
	// confirmar la actualización del estado de la tarea
	@FXML
    void onBtnActualizarTarea(ActionEvent event) {
		int idProyecto = proyecto.getId();
		EstadoTarea nuevoEstado = cbEstadoTarea.getValue();
		
		if(nuevoEstado != tarea.getEstado()) {
			if(verificarActualizacion()) {
				if (mainApp.getModelFactoryController().actualizarEstadoTarea(idProyecto, tarea.getId(), nuevoEstado)) {
					mostrarMensaje("Notificacion", "Tarea Actualizada", "La tarea ha sido actualizada con exito", AlertType.INFORMATION);
					mainApp.getModelFactoryController().guardarResourceBinario();
					
					// Luego de actualizar, notificar y almacenar la info, se
					// tiene que actualizar la informacion de la tarea visualmente				
					tarea.setEstado(nuevoEstado);
					inicializarDatos();
					
					// Y se notifica

					String equipo = mainApp.getEquipoUsuarioLogeado().getNombre();
					String nombreCompleto = mainApp.getUsuarioLogeado().getNombre()+" "+mainApp.getUsuarioLogeado().getApellido();
					boolean isCorreoEnviado;
					
					isCorreoEnviado = EmailService.enviarEmail("CAMU Notificaciones | Tarea Actualizada!"
			                ,"<!DOCTYPE html>\n" +
			                        "<html>\n" +
			                        "    <head>\n" +
			                        "        <title>Actualización de tarea</title>\n" +
			                        "    </head>\n" +
			                        "    <body>\n" +
			                        "        <p><strong>Camu!</strong></p>\n" +
			                        "        <p><strong>Hola, Lider del equipo "+ equipo + ". Bienvenido a Camu!</strong></p>\n" +
			                        "        <p>El usuario "+ nombreCompleto + " ha realizado un cambio a la tarea:</p>\n" +
			                        "        <p>Tarea: |ID-"+ tarea.getId() + "|  "+ tarea.getNombre() +" Asignada al proyecto: |ID-"+idProyecto+"| "+proyecto.getNombre()+"</p>\n" +
			                        "    </body>\n" +
			                        "</html>"
			                ,"julian.acostat@uqvirtual.edu.co");
					
					if(isCorreoEnviado)
						mostrarMensaje("Notifacion", "Notificacion enviada", "La notificacion del nuevo cambio se ha realizado", AlertType.INFORMATION);									
					else
						mostrarMensaje("Notifacion", "Notificado no enviada", "El correo no pudo enviar, error del sistema lo sentimos :c", AlertType.ERROR);
				}			
				else {
					mostrarMensaje("Error", "Tarea No Actualizada", "La tarea NO ha sido actualizada", AlertType.ERROR);
				}							
			}
			//else{}: No es necesario, si no aceptó actualizar la tarea, no se le 
			// 		  mostrará nada al usuario
		}
		else {
			mostrarMensaje("Advertencia", "Tarea No Actualizada", "No ha realizado ningún cambio a la tarea", AlertType.WARNING);
		}
		
    }

	// Este metodo NO actualiza, solo muestra el pane en el que se actualiza la tarea
    @FXML
    void onBtnIrActualizar(ActionEvent event) {
    	paneActualizar.setVisible(true);
    }

    @FXML
    void onBtnRegresar(ActionEvent event) {
    	paneActualizar.setVisible(false);
    }
    
	
    public void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType){
    	Alert alert = new Alert(alertType);
    	alert.setTitle(titulo);
    	alert.setHeaderText(header);
    	alert.setContentText(contenido);
    	alert.showAndWait();
    }
    
    public boolean verificarActualizacion() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmacion");
    	alert.setHeaderText("Actualizar Tarea");  	
    	alert.setContentText("Recuerde que al actualizar el estado de una tarea \nse le notificará al lider del proyecto sobre sus cambios realizados.");
    	
    	Optional<ButtonType> resultado = alert.showAndWait();
    	if(resultado.isPresent() && resultado.get() == ButtonType.OK)
    		return true;
    	else
    		return false;
    }

}
