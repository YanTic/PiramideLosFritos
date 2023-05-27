package App.Model;

public class Cliente {
	
	private String nombre;
	private String apellido;
	private String email;
	private String telefono;
	private String direccion;
	private String ciudad;
	private String membresia;
	private String afiliado_id;
	
	@SuppressWarnings("unused")
	private Cliente() {}

	public Cliente(String nombre, String apellido, String email, String telefono, 
			String direccion, String ciudad, String membresia, String afiliado_id) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.membresia = membresia;
		this.afiliado_id = afiliado_id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getMembresia() {
		return membresia;
	}

	public void setMembresia(String membresia) {
		this.membresia = membresia;
	}

	public String getAfiliado_id() {
		return afiliado_id;
	}

	public void setAfiliado_id(String afiliado_id) {
		this.afiliado_id = afiliado_id;
	}
	
}
