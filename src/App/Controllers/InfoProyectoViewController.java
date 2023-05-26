package App.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import App.MainApp;
import App.Controllers.dinamico.IntegranteController;
import App.Controllers.dinamico.TareaController;
import App.Model.Persona;
import App.Model.Proyecto;
import App.Model.Tarea;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class InfoProyectoViewController implements Initializable {

	@FXML private TabPane mainTabPane;
    @FXML private Label lb_nomProyecto;
    @FXML private Label lb_nomProyecto1;
    @FXML private Label lb_descProyecto;
    @FXML private Label lb_estadoProyecto;

    @FXML private GridPane gridPaneIntegrantes;
    @FXML private GridPane gridPaneTareas;
    @FXML private Button BtnActualizarVendedor;
    
    @FXML private Button btnUser;
    @FXML private Button btnLogout;
    @FXML private Button btnRegresar;
    

    private MainApp mainApp;
    ModelFactoryController modelFactoryController;
    Proyecto proyectoAsignado; 
    Persona usuario;
    ObservableList<Tarea> listaTareasData = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(()->{
			modelFactoryController =  mainApp.getModelFactoryController();
			inicializarVista();
		});				
	}
	
	public void setMainAppYProyecto(MainApp mainApp, Proyecto proyectoAsignado) {
		this.mainApp = mainApp;		
		this.proyectoAsignado = proyectoAsignado;
	}
   
	public void inicializarVista() {
		btnUser.setText(mainApp.getUsuarioLogeado().getNombre());
		
		lb_nomProyecto.setText(proyectoAsignado.getNombre());
		lb_nomProyecto1.setText(proyectoAsignado.getNombre());
	    lb_descProyecto.setText(proyectoAsignado.getDescripcion());
	    lb_estadoProyecto.setText(proyectoAsignado.getEstado().toString());
	    
	    inicializarIntegrantes();
	    inicializarTareas();
	}
	
	public void inicializarTareas() {
		ArrayList<Tarea> tareas = modelFactoryController.getListaTareasPorProyecto(proyectoAsignado.getId());
		System.out.println(tareas);
		
		gridPaneTareas.getChildren().clear();
		int fila = 0;	
		
		for(int i = 0; i<tareas.size(); i++) {
			try{
				// Cargamos la vista dinamica
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(MainApp.class.getResource("Views/dinamico/tarea.fxml"));
				
				// Agregamos la vista al gridpane de proyecto
				gridPaneTareas.add(fxmlLoader.load(), 0, fila);
				
				// No se usan columnas, porque solo será una, solo aumentarán las filas
				fila++;
				
				// Cargamos el controlador de la vista
				// El controlador solo se crea despues de cargar el fxml 
				// usando 'fxmlLoader.load()'
				TareaController tareaController = fxmlLoader.getController();
				tareaController.establecerDatos(mainApp, proyectoAsignado,tareas.get(i));				
			}
			catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	
	public void inicializarIntegrantes() {
		ArrayList<Persona> integrantes = mainApp.getEquipoUsuarioLogeado().getIntegrantes();
		
		gridPaneTareas.getChildren().clear();
		int fila = 0;	
		
		for(int i = 0; i<integrantes.size(); i++) {
			try{
				// Cargamos la vista dinamica
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(MainApp.class.getResource("Views/dinamico/integrante.fxml"));
				
				// Agregamos la vista al gridpane de proyecto
				gridPaneIntegrantes.add(fxmlLoader.load(), 0, fila);
				
				// No se usan columnas, porque solo será una, solo aumentarán las filas
				fila++;
				
				// Cargamos el controlador de la vista
				// El controlador solo se crea despues de cargar el fxml 
				// usando 'fxmlLoader.load()'
				IntegranteController integranteController = fxmlLoader.getController();
				integranteController.establecerDatos(mainApp, integrantes.get(i));				
			}
			catch (IOException e) {
				e.printStackTrace();
			}			
		}
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
    void onBtnRegresar(ActionEvent event) {
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
    
    public void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType){
    	Alert alert = new Alert(alertType);
    	alert.setTitle(titulo);
    	alert.setHeaderText(header);
    	alert.setContentText(contenido);
    	alert.showAndWait();
    }
    
    
// Este metodo recibiría el usuario que está logueado (o el equipo), pero como 
// no tenemos un Login no recibirá ningun parametro, por lo que la lista de tareas  
// que retorne serán las tareas de un equipo aleatorio (en este caso el equipo 1,
// en el cual se encuentra el usuario 1)
//    public ObservableList<Tarea> getTareasData(){
//		listaTareasData.addAll(modelFactoryController.getListaTareasPorEquipo(1)) ;
//		return listaTareasData;
//	}
	


}
