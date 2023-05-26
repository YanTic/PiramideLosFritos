package App.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Reunion implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String motivo;
	private ArrayList<Equipo> integrantes;
	
	public Reunion() {
		super();
	}

	public Reunion(int id, String motivo, ArrayList<Equipo> integrantes) {
		super();
		this.id = id;
		this.motivo = motivo;
		this.integrantes = integrantes;
	}


//-------------------------------------Getters y Setters--------------------------------------------------------
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


	public ArrayList<Equipo> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(ArrayList<Equipo> integrantes) {
		this.integrantes = integrantes;
	}

}
