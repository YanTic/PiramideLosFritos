package App.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import App.MainApp;
import App.Controllers.dinamico.ProyectoController;
import App.Model.Mensaje;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class ColaboracionViewController implements Initializable {

	@FXML private TabPane mainTabPane;
    
    @FXML private HBox onlineUsersHbox;
    @FXML private Label lb_usuario_amigo;
    @FXML private TextArea txt_mensajes;
    @FXML private TextArea messageBox;
    @FXML private Button btnSend;
    @FXML private Button btnDoc;
    @FXML private Button btnLogout;
    @FXML private Button btnUser;
    
    @FXML private Label lb_Equipo_Persona;
    @FXML private GridPane gridPaneProyectos;

    private MainApp mainApp;
    ModelFactoryController modelFactoryController;
    Proyecto proyectoAsignado; 
    Tarea tareaSeleccionada;
    Persona usuario;
    ObservableList<Tarea> listaTareasData = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(()->{
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
		inicializarChat();
		inicializarMenuProyectos();
	}
	
	private void inicializarMenuProyectos() {
		ArrayList<Proyecto> proyectos = modelFactoryController.getProyectosPorPersona(mainApp.getUsuarioLogeado().getId());
		lb_Equipo_Persona.setText("Equipo #"+modelFactoryController.getEquipoPorPersona(mainApp.getUsuarioLogeado().getId()).getId());
		
		// Recordar: Para proyectos con gridpanes dinamica, en el scenebuilder, siempre
		// dejar el gridpane sin filas ni columnas (bueno, una si). De tal manera
		// que quede en el SceneBuilder como: GridPane (1 x 0).
		// Esto se hace porque ya le estamos agregando la cantidad de elementos que
		// va a tener, cuantas filas y columnas en esta funcion, y ya el tamaño es
		// segun el AnchorPane de la vista dinamica que creamos (proyecto.fxml) 
		gridPaneProyectos.getChildren().clear();
		int fila = 0;
		int columna = 0;		
		
		for(int i = 0; i<proyectos.size(); i++) {
			try{
				// Cargamos la vista dinamica
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(MainApp.class.getResource("Views/dinamico/proyecto.fxml"));
												
				// Por estetica, solo se va a permitir ver dos proyectos por fila 
				// Luego de haber dos en la fila, creamos otra fila.
				if(columna == 2) {
					columna = 0;
					fila++;
				}
				
				// Agregamos la vista al gridpane de proyecto
				gridPaneProyectos.add(fxmlLoader.load(), ++columna, fila);
				
				// Cargamos el controlador de la vista
				// El controlador solo se crea despues de cargar el fxml 
				// usando 'fxmlLoader.load()'
				ProyectoController proyectoController = fxmlLoader.getController();
				proyectoController.establecerDatos(proyectos.get(i), mainApp);
				
			}
			catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
	}
	
	private void inicializarChat() {
		// ---------------------------- INFO CHAT ----------------------------
		Persona amigo = modelFactoryController.getPersonaPorId(2);
		System.out.println("amigo: "+amigo.getNombre()+" id: "+amigo.getId());
		lb_usuario_amigo.setText(amigo.getNombre());
		
		usuario = modelFactoryController.getPersonaPorId(1);
		
		cargarMensajesChat(usuario);
		
	}
	
	
	private void cargarMensajesChat(Persona pers) {
		ArrayList<Mensaje> mensajes = pers.getMensajes();		
		
		try {
			for(Mensaje m : mensajes) {
				txt_mensajes.appendText(m.getContenido());
			}	
		}
		catch(NullPointerException e) {
			System.out.println("No hay mensajes");
		}
		
	}

    @FXML
    void onBtnSend(ActionEvent event) {
    	txt_mensajes.appendText("\n"+messageBox.getText());
    	Mensaje nuevoMensaje = new Mensaje("\n"+messageBox.getText());
    	
    	modelFactoryController.agregarMensaje(usuario.getId(), nuevoMensaje);
    	modelFactoryController.guardarResourceBinario();
    }
    
    @FXML
    void onBtnDoc(ActionEvent event) {
    	subirImagen();
    }
    
    public void subirImagen(){
		// File chooser que solo permita subir archivos de tal tipo (segun requisitos)
		FileChooser fileChooser = new FileChooser();
	    FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
    	FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
    	FileChooser.ExtensionFilter ext3 = new FileChooser.ExtensionFilter("PDF files(*.pdf)","*.PDF");
    	FileChooser.ExtensionFilter ext4 = new FileChooser.ExtensionFilter("DOCX files(*.docx)",".DOCX");
    	FileChooser.ExtensionFilter ext5 = new FileChooser.ExtensionFilter("PPTX files(*.pptx)",".pptx");
    	FileChooser.ExtensionFilter ext6 = new FileChooser.ExtensionFilter("XLS files(*.xls)",".xls");
    	FileChooser.ExtensionFilter ext7 = new FileChooser.ExtensionFilter("TXT files(*.txt)",".txt");
    																	   
    	fileChooser.getExtensionFilters().addAll(ext1, ext2, ext3, ext4, ext5, ext6, ext7);
    	
    	File archivoSeleccionado = fileChooser.showOpenDialog( this.btnSend.getScene().getWindow() );
    	
    	System.out.println(archivoSeleccionado.getName());

    	txt_mensajes.appendText("\n"+archivoSeleccionado.getName());
    	Mensaje nombreDoc = new Mensaje("\n"+messageBox.getText()+archivoSeleccionado.getName());
    	
    	modelFactoryController.agregarMensaje(usuario.getId(), nombreDoc);
//    	try {    		        		
//	    	BufferedImage bf;	
//	    	
//	    	if(archivoSeleccionado != null){
//	    		// Leo la imagen para luego mostrarla en el ImageView
//				bf = ImageIO.read(archivoSeleccionado);
//				
//	    		Imageimagen = SwingFXUtils.toFXImage(bf, null);
//	    		imagenViewProducto.setImage(imagen);    	   
//	    		
//	    		// Hacer una copia de la imagen porque la imagen le pertenece a la ruta especifica del usuario
//	    		// Y guardo la nueva ruta para asignarsela al producto
//	    		if(productoSeleccionado != null){
//	    			rutaImagenProductoSeleccionado = crudVendedorViewController.copiarImagen(productoSeleccionado.getNombre(),
//																		    				 archivoSeleccionado.getAbsolutePath());    	    		    	    			   
//	    		} 
//	    		else{ // Esto me permite guardar la imagen de un nuevo producto sin hacer seleccion en la tabla 
//	    			rutaImagenNuevoProducto = crudVendedorViewController.copiarImagen(txtNombreProducto.getText(),
//		    				 														  archivoSeleccionado.getAbsolutePath());
//	    		}
//	    	
//	    	}
//	    	else{
//	    		mostrarMensaje("Notifacion", "Archivo NO valido", "El archivo no ha sido encontrado", AlertType.ERROR);
//	    	}
//    	} catch (IOException e) {
//			e.printStackTrace();
//		}	
    	
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
	
    
  /*  
    @FXML
    void onBtnDoc(ActionEvent event) {
    	enviarDocumento();
    }
  */

//    @FXML
//    void enviarDocumento() {
//        // Crear un objeto FileChooser para permitir al usuario seleccionar el archivo a cargar
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Seleccionar documento");
//        File file = fileChooser.showOpenDialog(null);
//
//        if (file != null) {
//            // Leer el contenido del archivo
//            String contenido = null;
//            try {
//                contenido = new String(Files.readAllBytes(file.toPath()));
//            } catch (IOException e) {
//                // Manejar cualquier excepción de lectura de archivo aquí
//                e.printStackTrace();
//            }
//
//           
//
//            // Enviar el documento a los demás miembros del equipo utilizando un método de comunicación
//            // como un socket o un servicio web
//            // ...
//            // Obtener el documento seleccionado   // Crear un nuevo objeto Documento con el nombre y contenido del archivo cargado
//            Documento documento = new Documento( file.getName(), contenido);
//
//                    // Agregar el documento a la lista de documentos colaborativos
//                    Domain.agregarDocumento(documento);// Código para obtener el documento seleccionado
//
//            // Establecer la conexión con el servidor
//            try (Socket socket = new Socket("localhost",1056)) {
//                // Obtener el OutputStream de la conexión
//                OutputStream outputStream = socket.getOutputStream();
//
//                // Escribir los datos del documento en el OutputStream
//                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//                dataOutputStream.writeUTF(documento.getNombre()); // Escribir el nombre del documento
//                dataOutputStream.writeUTF(documento.getContenido()); // Escribir el contenido del documento
//
//                // Cerrar el OutputStream y la conexión
//                outputStream.close();
//            } catch (IOException e) {
//                // Manejar el error de conexión
//                e.printStackTrace();
//            }
//        }
//
//        }
}