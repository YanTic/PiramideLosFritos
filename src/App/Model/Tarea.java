package App.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Tarea implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private String descripción;
	private EstadoTarea estado;
	private LocalDate fechaCreacion;
	private int prioridad; // Escala 1 - 4 (Muy importante - No importante)
	
	public Tarea() {
		super();
	}

	public Tarea(String id, String nombre, String descripción, EstadoTarea estado, LocalDate fechaCreacion, int prioridad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripción = descripción;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.prioridad = prioridad;
	}

	//-------------------------------------------Getters y Setters--------------------------------
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripción() {
		return descripción;
	}

	public void setDescripción(String descripción) {
		this.descripción = descripción;
	}

	public EstadoTarea getEstado() {
		return estado;
	}

	public void setEstado(EstadoTarea estado) {
		this.estado = estado;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

	@Override
	public String toString() {
		return "Tarea [id=" + id + ", nombre=" + nombre + ", descripción=" + descripción + ", estado=" + estado
				+ ", fechaCreacion=" + fechaCreacion + ", prioridad=" + prioridad + "]";
	}

}