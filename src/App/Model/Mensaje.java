package App.Model;

import java.io.Serializable;

public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;
	private String contenido;
	
	public Mensaje() {
		super();
	}

	public Mensaje(String contenido) {
		this.contenido = contenido;
	}
	
	//---------------------------Getters y Setters-----------------------------------------------

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@Override
	public String toString() {
		return "Mensaje [contenido=" + contenido + "]";
	}

}
