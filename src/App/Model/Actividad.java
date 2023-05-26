package App.Model;

import java.io.Serializable;

public class Actividad implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String descripcion;
	
	public Actividad() {
		super();
	}

	public Actividad(String id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

//------------------------------Getters y Setters---------------------------------------------------
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
