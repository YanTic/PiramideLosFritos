package App.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String apellido;
	private String email;
	private String contrasenia;
	private LocalDate fechaNacimiento;
	private ArrayList<Documento> documentos;
	private ArrayList<Mensaje> mensajes;

	public Persona() {
		super();
	}

	public Persona(int id, String nombre, String apellido, String contrasenia, String email,
			LocalDate fechaNacimiento, ArrayList<Documento> documentos, ArrayList<Mensaje> mensajes) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasenia = contrasenia;
		this.fechaNacimiento = fechaNacimiento;
		this.documentos = documentos;
		this.mensajes = mensajes;
	}


	//---------------------------------------Getters y setters-------------------------------------
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public ArrayList<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(ArrayList<Documento> documentos) {
		this.documentos = documentos;
	}

	public ArrayList<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(ArrayList<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
				+ ", contrasenia=" + contrasenia + ", fechaNacimiento=" + fechaNacimiento + ", documentos=" + documentos
				+ ", mensajes=" + mensajes + "]";
	}
	
}
