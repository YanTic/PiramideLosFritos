package App.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Proyecto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String descripcion;
	private EstadoProyecto estado;
	private String nombre;
	private ArrayList<Tarea> tareas;
	
	public Proyecto() {
		super();
	}

	public Proyecto(int id, String descripcion, EstadoProyecto estado, String nombre, 
			ArrayList<Tarea> tareas) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.estado = estado;
		this.nombre = nombre;
		this.tareas = tareas;
	}

	//-------------------------------Getters y Setter----------------------------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(ArrayList<Tarea> tareas) {
		this.tareas = tareas;
	}

	@Override
	public String toString() {
		return "Proyecto [id=" + id + ", descripcion=" + descripcion + ", estado=" + estado + ", nombre=" + nombre
				+ "]";
	}
	
}
