package App;
import java.io.IOException;

import App.Controllers.LoginViewController;
import App.Controllers.ModelFactoryController;
import App.Model.Equipo;
import App.Model.Persona;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private ModelFactoryController modelFactoryController;
	Persona usuarioLogeado;
	Equipo equipoUsuarioLogeado;
//	Domain domain;	


	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Modulo Colaboracion");
		
		// Creamos la instancia del ModelFactory, la cual crea los datos.
		// Como cada vista se van asignado la MainApp, esta contendrá
		// la instancia del ModelFactory
		modelFactoryController = ModelFactoryController.getInstance();
		
		initRootLayout();
	}
	
	/**
	 * Initializes the Root Layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Views/Login.fxml"));
			AnchorPane colaboracionOverview = (AnchorPane) loader.load();
			
			// Give the con+troller access to the main app.
			// Le doy el acceso al controlodar de la main app
			LoginViewController controller = loader.getController();
			controller.setMainApp(this);
			
			// Show the scene containing the root layout.
			Scene scene = new Scene(colaboracionOverview);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public BorderPane getRootLayout() {
		return rootLayout;
	}
	
	public ModelFactoryController getModelFactoryController() {
		return modelFactoryController;
	}
	
	public void setUsuarioLogeado(Persona usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
		
		if (this.usuarioLogeado != null)
			equipoUsuarioLogeado = modelFactoryController.getEquipoPorPersona(usuarioLogeado.getId());			
	}

	public Persona getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public Equipo getEquipoUsuarioLogeado() {
		return equipoUsuarioLogeado;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
