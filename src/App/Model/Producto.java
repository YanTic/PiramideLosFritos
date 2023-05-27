package App.Model;

public class Producto {
	
	private String nombre;
	private String precio;
	private String unidadesDisponibles;
	private String descripcion;
	private String categoria;
	
	public Producto() {}

	public Producto(String nombre, String precio, String unidadesDisponibles, 
			String descripcion, String categoria) {
		this.nombre = nombre;
		this.precio = precio;
		this.unidadesDisponibles = unidadesDisponibles;
		this.descripcion = descripcion;
		this.categoria = categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getUnidadesDisponibles() {
		return unidadesDisponibles;
	}

	public void setUnidadesDisponibles(String unidadesDisponibles) {
		this.unidadesDisponibles = unidadesDisponibles;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	};
	
}
