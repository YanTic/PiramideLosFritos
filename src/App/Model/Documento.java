package App.Model;

import java.io.Serializable;

public class Documento implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String contenido;
	

	public Documento() {
		super();
	}

	
	public Documento(String nombre, String contenido) {
		super();
		
		this.nombre = nombre;
		this.contenido = contenido;
	}

	//---------------------------------getteres y setters------------------------------------------
	

	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@Override
	public String toString() {
		return "Documento [ nombre=" + nombre + ", contenido=" + contenido + "]";
	}
	
}
