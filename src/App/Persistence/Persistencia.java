package App.Persistence;

import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import App.Model.Domain;


public class Persistencia {

//	public static final String RUTA_ARCHIVO_LOG = "src/resources/MarketplaceLog.txt";
//	public static final String RUTA_ARCHIVO_DOMAIN_BINARIO_2 = "D:/IdeaProjects/ModuloColaboracion/resources/model.dat";
//	public static final String RUTA_ARCHIVO_DOMAIN_BINARIO_3 = "D:/DATOS/model.dat";

	// RUTAS
	public static final String RUTA_ARCHIVO_DOMAIN_BINARIO = "src/resources/model.dat";

	
	//------------------------------------SERIALIZACIÓN  y XML
	public static Domain cargarRecursoDomainBinario() {		
		Domain domain = null;
		
		try {
			domain = (Domain) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_DOMAIN_BINARIO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return domain;
	}
	
	public static void guardarRecursoDomainBinario(Domain domain) {
		try { ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_DOMAIN_BINARIO, domain);} 
		catch (Exception e) {e.printStackTrace();}
	}
	
	
	
	public static String getFechaSistema(){
		return ArchivoUtil.fechaSistema();
	}
	
	
//	public static void cargarDatosArchivos(Domain domain) throws FileNotFoundException, IOException {
//		
//// Cargar archivo de vendedores (Recordar: Los vendedores contienen sus productos,
//// por lo tanto, esto tambien carga los productos de cada vendedor)
//ArrayList<Vendedor> vendedoresCargados = cargarVendedores();
//
//if(vendedoresCargados.size() > 0)
//	marketplace.getListaVendedores().addAll(vendedoresCargados);
//
//
//// Cargar usuarios
//ArrayList<Usuario> usuariosCargados = cargarUsuarios();
//
//if(usuariosCargados.size() > 0)
//	marketplace.getListaUsuarios().addAll(usuariosCargados);
//
//
//
//
//
//
//
//////cargar archivos empleados
////ArrayList<Empleado> empleadosCargados = cargarEmpleados();
////
////if(empleadosCargados.size() > 0)
////	banco.getListaEmpleados().addAll(empleadosCargados);
////
//
//
//
////cargar archivo objetos
//
////cargar archivo empleados
//
////cargar archivo prestamo
//
//}	
	
//	public static String copiarImagen(String nombreProducto, String rutaAbsolutaArchivo){
//	String rutaDestino = "";  
//	
//	try {
//		
//		System.out.println("Ruta Absoluta Original: "+rutaAbsolutaArchivo);
//		
//		// Ejemplo: src/resources/imagenesProducto/imagen_nombreProducto.png
//		rutaDestino = RUTA_ARCHIVO_IMAGEN_PRODUCTOS + "imagen_" + nombreProducto + ".png";
//
//		System.out.println("Ruta Destino: "+rutaDestino);
//		
//		ArchivoUtil.copiarArchivo(rutaAbsolutaArchivo, rutaDestino);
//		
//	} catch (IOException e) {
//		// TODO Auto-generated method stub
//		rutaDestino = "";
//		e.printStackTrace();
//	} 
//	
//	return rutaDestino;
//	
//}

}
