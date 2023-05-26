package App.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Domain implements Serializable {
	
	private static final long serialVersionUID = 1L;
	ArrayList<Persona> listaPersonas = new ArrayList<>();
	ArrayList<Equipo> listaEquipos = new ArrayList<>();
	ArrayList<Proyecto> listaProyectos = new ArrayList<>();
	ArrayList<Reunion> listaReuniones = new ArrayList<>();
	static ArrayList<Documento> documentos = new ArrayList<> ();
	
	public Domain() {
		
	}

	

	
	public boolean verificarUsuario(String nombreUsuario, String contrasenia) {
		boolean flagUsuarioExistente = false;
		
		// Esto compara el nombre de usuario y su contrasenia para verificar 
		// si se encuentra en la lista
		for(Persona usuario: listaPersonas){
			if(usuario.getNombre().equalsIgnoreCase(nombreUsuario) &&
			   usuario.getContrasenia().equals(contrasenia)){
				flagUsuarioExistente = true;
				break;
			}
		}
		
		return flagUsuarioExistente;
	}
	
	

	public ArrayList<Tarea> getListaTareasPorProyecto(int idProyecto) {
		return getProyecto(idProyecto).getTareas();
	}
	

	public Tarea getTareaPorIdYProyecto(String idTarea, int idProyecto) {
		ArrayList<Tarea> tareas = getListaTareasPorProyecto(idProyecto);
		Tarea tarea = null;
		
		for(Tarea t : tareas) {
			if(t.getId() == idTarea || t.getId().equalsIgnoreCase(idTarea)) {
				tarea = t;
				break;
			}
		}
		
		return tarea;
	}

	
	public Equipo getEquipoPorId(int idEquipo) {
		Equipo equipo = null;
		
		for(Equipo e: getListaEquipos()){
			if(e.getId() == idEquipo){
				equipo = e;
				break;
			}
		}
		
		return equipo;
	}
	
	
	public Proyecto getProyecto(int idProyecto) {
		Proyecto proyectoEncontrado = null;

		for (Proyecto p : getListaProyectos()) {
			if(p.getId() == idProyecto) {
				proyectoEncontrado = p;
				break;
			}
		}

		return proyectoEncontrado;	
	}
	
	
	// Recordemos: Un proyecto solo puede hacer parte de un equipo (se le 
	// asigna solo a uno), un equipo puede tener varios proyectos
	public boolean actualizarEstadoTarea(int idProyecto, String idTarea, EstadoTarea nuevoEstado) {
		Boolean flagActualizada = false;
		Proyecto proyecto = getProyecto(idProyecto);
		
		if(proyecto != null){
			getTareaPorIdYProyecto(idTarea, idProyecto).setEstado(nuevoEstado);
			flagActualizada = true;
		}			
		else{
			System.out.println("No se ha podido actualizar");
		}
		
		return flagActualizada;
	}		
	
	public Persona getPersonaPorId(int idPersona) {
		Persona personaEncontrada = null;

		for (Persona p : getListaPersonas()) {
			if(p.getId() == idPersona) {
				personaEncontrada = p;
				break;
			}
		}

		return personaEncontrada;	
	}
	
	public Persona getPersonaPorUsuarioYContrasenia(String user, String contrasenia) {
		Persona personaEncontrada = null;

		for (Persona p : getListaPersonas()) {
			if((p.getNombre() == user || p.getNombre().equals(user)) &&
			   (p.getContrasenia() == contrasenia || p.getContrasenia().equals(contrasenia))) {
				personaEncontrada = p;
				break;
			}
		}

		return personaEncontrada;	
	}
	

	// Acá podemos obtener directamente los proyectos por el equipo en el que se
	// encuentra la persona | Según las reglas del negocio y segun el modelado,
	// una persona solo pueda hacer parte de un solo equipo.
	public ArrayList<Proyecto> getProyectosPorPersona(int idPersona) {
		ArrayList<Proyecto> proyectos = null;
		
		if(getPersonaPorId(idPersona) != null) {
			Equipo equipoPersona = getEquipoPorPersona(idPersona);
			proyectos = equipoPersona.getProyectos();
		}
		
		return proyectos;
	}
	
	public Equipo getEquipoPorPersona(int idPersona) {
		Equipo equipo = null;
		
		for(Equipo e : getListaEquipos()) {
			if(isPersonaInEquipo(idPersona, e.getId())){
				equipo = e;
				break;
			}
		}
		
		return equipo;
	}
	
	public boolean isPersonaInEquipo(int idPersona, int idEquipo) {
		Equipo equipo = getEquipoPorId(idEquipo);
		
		if(equipo != null) {
			for(Persona p : equipo.getIntegrantes()) {
				if(p.getId() == idPersona) {
					return true;
				}
			}
		}
		
		return false;
	}

	

	public boolean agregarMensaje(int idPersona, Mensaje mensaje) {		
		if(getPersonaPorId(idPersona)!=null) {
			System.out.println("idPe: "+idPersona+ " msj: "+mensaje.getContenido());
			getPersonaPorId(idPersona).getMensajes().add(mensaje);
			return true;	
		}
		
		return false;
	}
	
	
	public static void agregarDocumento(Documento documento) {
        documentos.add(documento);
    }

    public void removerDocumento(Documento documento) {
        documentos.remove(documento);
    }
    
    
    public static ArrayList<Documento> obtenerDocumentos() {
        return documentos;
    }
    
    
	
	// -------------------------- GETTERS Y SETTERS COMUNES --------------------------  
	public ArrayList<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(ArrayList<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public ArrayList<Equipo> getListaEquipos() {
		return listaEquipos;
	}

	public void setListaEquipos(ArrayList<Equipo> listaEquipos) {
		this.listaEquipos = listaEquipos;
	}

	public ArrayList<Proyecto> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(ArrayList<Proyecto> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public ArrayList<Reunion> getListaReuniones() {
		return listaReuniones;
	}

	public void setListaReuniones(ArrayList<Reunion> listaReuniones) {
		this.listaReuniones = listaReuniones;
	}

	public static ArrayList<Documento> getDocumentos() {
		return documentos;
	}


	public static void setDocumentos(ArrayList<Documento> documentos) {
		Domain.documentos = documentos;
	}

}
