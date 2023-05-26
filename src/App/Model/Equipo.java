package App.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Equipo implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private EstadoEquipo estado;
	private ArrayList<Persona> integrantes;
	private ArrayList<Proyecto> proyectos;
	
	
	public Equipo() {
		super();
	}
	
	
	public Equipo(int id, String nombre, EstadoEquipo estado, ArrayList<Persona> integrantes, 
			ArrayList<Proyecto> proyectos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.estado = estado;
		this.integrantes = integrantes;
		this.proyectos = proyectos;
	}

	//-------------------------------------getters y setters-----------------------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EstadoEquipo getEstado() {
		return estado;
	}

	public void setEstado(EstadoEquipo estado) {
		this.estado = estado;
	}
	
	public ArrayList<Persona> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(ArrayList<Persona> integrantes) {
		this.integrantes = integrantes;
	}

	public ArrayList<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(ArrayList<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	
	@Override
	public String toString() {
		return "Equipo [id=" + id + ", nombre=" + nombre + ", estado=" + estado + "]";
	}

}
