package App.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import App.Model.Documento;
import App.Model.Domain;
import App.Model.Equipo;
import App.Model.EstadoEquipo;
import App.Model.EstadoProyecto;
import App.Model.EstadoTarea;
import App.Model.Mensaje;
import App.Model.Persona;
import App.Model.Proyecto;
import App.Model.Reunion;
import App.Model.Tarea;
import App.Persistence.Persistencia;

public class ModelFactoryController {

	Domain domain;
	
	//------------------------------  Singleton ------------------------------------------------
	// Clase estatica oculta. Tan solo se instanciara el singleton una vez
	private static class SingletonHolder {
		// El constructor de Singleton puede ser llamado desde aquí al ser protected
		private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
	}

	// Método para obtener la instancia de nuestra clase
	public static ModelFactoryController getInstance() {
		return SingletonHolder.eINSTANCE;
	}
		
	public ModelFactoryController() {
		if(domain == null){
			System.out.println("DOMAIN ES NULL");
			
			inicializarDatos();			
			guardarResourceBinario();
			
//			cargarResourceBinario();
		}
	}
		
	
	private void inicializarDatos() {
		domain = new Domain();
		
		// Creacion de personas
		Persona persona = new Persona();
		persona.setId(1);
		persona.setNombre("Ana");
		persona.setApellido("Rodriguez");
		persona.setContrasenia("ana123");
		persona.setEmail("ana@email.com");
		persona.setFechaNacimiento(LocalDate.of(1995, 12, 31));
		persona.setDocumentos(null);
			ArrayList<Mensaje> msgs = new ArrayList<>();
			msgs.add(new Mensaje("Holaa"));
		persona.setMensajes(msgs);
		domain.getListaPersonas().add(persona);
		
		persona = new Persona();
		persona.setId(2);
		persona.setNombre("Carlos");
		persona.setApellido("Gonzalez");
		persona.setContrasenia("carlos123");
		persona.setEmail("carlos@email.com");
		persona.setFechaNacimiento(LocalDate.of(1980, 5, 15));
		persona.setDocumentos(null);
		persona.setMensajes(null);
		domain.getListaPersonas().add(persona);

		persona = new Persona();
		persona.setId(3);
		persona.setNombre("Julia");
		persona.setApellido("Martinez");
		persona.setContrasenia("julia123");
		persona.setEmail("julia@email.com");
		persona.setFechaNacimiento(LocalDate.of(1998, 2, 28));
		persona.setDocumentos(null);
		persona.setMensajes(null);
		domain.getListaPersonas().add(persona);

		// Creacion de proyectos
		Proyecto proyecto = new Proyecto();
		proyecto.setId(1);
		proyecto.setNombre("Construcción de un Puente");
		proyecto.setDescripcion("Diseñar y construir un puente protegiendo la áreas verdes de la zona");
		proyecto.setEstado(EstadoProyecto.INICIADO);
			ArrayList<Tarea> tareas = new ArrayList<>();
			tareas.add(new Tarea("1", "Estudio de viabilidad y planificación", "Realizar un análisis exhaustivo del terreno, condiciones climáticas y de tráfico para determinar la viabilidad del proyecto", EstadoTarea.SIN_INICIAR, LocalDate.of(2022, 2, 10), 1));		
			tareas.add(new Tarea("2", "Diseño estructural del puente", "Realizar el diseño de la estructura del puente, incluyendo los materiales y tecnologías a utilizar", EstadoTarea.SIN_INICIAR, LocalDate.of(2022, 2, 10), 1));
			tareas.add(new Tarea("3", "Adquisición y transporte de materiales", "Adquirir los materiales necesarios para la construcción del puente", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 12), 2));
			tareas.add(new Tarea("4", "Pruebas y evaluaciones", "Realizar pruebas y evaluaciones rigurosas para garantizar que el puente cumple con los estándares de calidad y seguridad aplicables", EstadoTarea.TERMINADO, LocalDate.of(2022, 2, 14), 2));
		proyecto.setTareas(tareas);
		domain.getListaProyectos().add(proyecto);
		
		proyecto = new Proyecto();
		proyecto.setId(2);
		proyecto.setNombre("Construcción de una planta procesadora de alimentos");
		proyecto.setDescripcion("Diseñar y construir una planta procesadora de alimentos para la producción en masa de productos alimenticios. Incluye la construcción de áreas de producción, almacenamiento y oficinas administrativas");
		proyecto.setEstado(EstadoProyecto.INICIADO);
			tareas = new ArrayList<>();
			tareas.add(new Tarea("1", "Diseño arquitectónico", "Crear los planos y diseños arquitectónicos para la construcción de la planta procesadora de alimentos, tomando en cuenta las necesidades y requisitos específicos de la industria alimentaria", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 10), 1));
			tareas.add(new Tarea("2", "Selección de equipos", "Seleccionar los equipos necesarios para la planta procesadora de alimentos, como maquinaria para el procesamiento de alimentos, sistemas de refrigeración, sistemas de empaque, entre otros", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 15), 2));
			tareas.add(new Tarea("3", "Gestión de permisos y licencias", "Obtener los permisos y licencias necesarios para la construcción y operación de la planta procesadora de alimentos, asegurándose de cumplir con todas las regulaciones", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 16), 3));
			tareas.add(new Tarea("4", "Instalación de equipos", "Instalar los equipos en la planta procesadora de alimentos, verificando que se hayan cumplido las especificaciones técnicas y que se hayan seguido los protocolos de seguridad", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 16), 3));
			tareas.add(new Tarea("5", "Pruebas y ajustes", "Realizar pruebas y ajustes en los equipos y sistemas de la planta procesadora de alimentos, para asegurarse de que estén funcionando correctamente y cumpliendo con los requisitos de calidad", EstadoTarea.SIN_INICIAR, LocalDate.of(2022, 2, 16), 4));
		proyecto.setTareas(tareas);
		domain.getListaProyectos().add(proyecto);

		proyecto = new Proyecto();
		proyecto.setId(3);
		proyecto.setNombre("Ampliación de una universidad");
		proyecto.setDescripcion("Diseñar y construir nuevas instalaciones para una universidad, incluyendo aulas, biblioteca, laboratorios y áreas comunes para estudiantes");
		proyecto.setEstado(EstadoProyecto.TERMINADO);
			tareas = new ArrayList<>();
			tareas.add(new Tarea("1", "Diseño de planos arquitectónicos", "Se deberán diseñar los planos para la ampliación de la universidad, tomando en cuenta las necesidades de los estudiantes y docentes", EstadoTarea.TERMINADO, LocalDate.of(2022, 2, 10), 1));
			tareas.add(new Tarea("2", "Construcción de edificios", "Se deberá llevar a cabo la construcción de los nuevos edificios de acuerdo con los planos y las especificaciones establecidas, garantizando la calidad y la seguridad de los trabajadores y futuros usuarios", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 20), 1));
			tareas.add(new Tarea("3", "Instalación de servicios básicos", "Se deberán instalar los servicios básicos como electricidad, agua, alcantarillado y sistemas de aire acondicionado y calefacción en los nuevos edificios para asegurar el bienestar de los usuarios", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 23), 2));
			tareas.add(new Tarea("4", "Inauguración", "Se deberá organizar una ceremonia de inauguración para presentar oficialmente los nuevos edificios y servicios a la comunidad universitaria y la sociedad en general", EstadoTarea.SIN_INICIAR, LocalDate.of(2022, 2, 25), 1));			
		proyecto.setTareas(tareas);
		domain.getListaProyectos().add(proyecto);
		
		proyecto = new Proyecto();
		proyecto.setId(4);
		proyecto.setNombre("Construcción de Hospital");
		proyecto.setDescripcion("proyecto destinado a la construcción de un hospital de tres pisos con capacidad para 200 camas en el centro de la ciudad");
		proyecto.setEstado(EstadoProyecto.INICIADO);
			tareas = new ArrayList<>();
			tareas.add(new Tarea("1", "Estudio de viabilidad", "Se deberá realizar un estudio para determinar la factibilidad del proyecto, tomando en cuenta factores como la demanda de servicios de salud en la zona, la disponibilidad de recursos y la regulación gubernamental", EstadoTarea.TERMINADO, LocalDate.of(2022, 2, 11), 1));
			tareas.add(new Tarea("2", "Diseño arquitectónico", "Se deberán diseñar los planos y especificaciones técnicas del edificio, tomando en cuenta las necesidades de los pacientes, los médicos y los trabajadores del hospital, así como las normas y estándares de construcción", EstadoTarea.TERMINADO, LocalDate.of(2022, 2, 11), 1));
			tareas.add(new Tarea("3", "Preparación del terreno", "Se deberá limpiar y preparar el terreno donde se construirá el hospital, para garantizar la estabilidad y seguridad de la estructura", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 13), 1));
			tareas.add(new Tarea("4", "Construcción de edificios", "Se deberá llevar a cabo la construcción de los edificios del hospital, siguiendo las especificaciones y planos establecidos y asegurando la calidad y seguridad de los trabajadores y futuros pacientes", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 17), 2));
			tareas.add(new Tarea("5", "Instalación de servicios", "Se deberán instalar los servicios necesarios en el hospital, incluyendo electricidad, agua, saneamiento, sistemas de ventilación y aire acondicionado, para garantizar la comodidad y bienestar de los pacientes, médicos y trabajadores", EstadoTarea.SIN_INICIAR, LocalDate.of(2022, 2, 17), 1));
			tareas.add(new Tarea("6", "Equipamiento del hospital", "Se deberá adquirir y equipar el hospital con los equipos y materiales necesarios para brindar servicios de salud, incluyendo camas, instrumentos médicos, sistemas informáticos, entre otros", EstadoTarea.SIN_INICIAR, LocalDate.of(2022, 2, 18), 2));			
		proyecto.setTareas(tareas);
		domain.getListaProyectos().add(proyecto);
		
		proyecto = new Proyecto();
		proyecto.setId(5);
		proyecto.setNombre("Edificación de un complejo deportivo");
		proyecto.setDescripcion("proyecto para construir un complejo deportivo con una piscina olímpica, canchas de tenis, baloncesto y voleibol");
		proyecto.setEstado(EstadoProyecto.SIN_INICIAR);
			tareas = new ArrayList<>();
			tareas.add(new Tarea("1", "Estudio de suelos", "Se deberá realizar un estudio del terreno donde se construirá el complejo deportivo, con el objetivo de conocer las condiciones geológicas del suelo y determinar si es apto para soportar la construcción", EstadoTarea.TERMINADO, LocalDate.of(2022, 2, 16), 1));
			tareas.add(new Tarea("2", "Diseño de estructuras", "Se deberán diseñar las estructuras del complejo deportivo, incluyendo los edificios principales, las canchas y los espacios para el público, teniendo en cuenta las normativas y estándares de seguridad requeridos", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 18), 3));
			tareas.add(new Tarea("3", "Construcción de la estructura", "Se deberá llevar a cabo la construcción de la estructura del complejo deportivo, garantizando la calidad y la seguridad de los trabajadores y futuros usuarios", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 19), 2));
		proyecto.setTareas(tareas);
		domain.getListaProyectos().add(proyecto);

		proyecto = new Proyecto();
		proyecto.setId(6);
		proyecto.setNombre("Ampliación de acueducto");
		proyecto.setDescripcion("Se ampliará el acueducto para abastecer a una zona con mayor población");
		proyecto.setEstado(EstadoProyecto.SIN_INICIAR);
			tareas = new ArrayList<>();
			tareas.add(new Tarea("1", "Estudio de factibilidad", "Se deberá realizar un estudio técnico y financiero para determinar la viabilidad de la ampliación del acueducto y establecer los recursos necesarios para llevar a cabo el proyecto", EstadoTarea.TERMINADO, LocalDate.of(2022, 2, 15), 1));
			tareas.add(new Tarea("2", "Diseño de planos y especificaciones técnicas", "Se deberán diseñar los planos y especificaciones técnicas necesarias para la ampliación del acueducto, tomando en cuenta las necesidades de la población y las condiciones del terreno", EstadoTarea.TERMINADO, LocalDate.of(2022, 2, 16), 1));
			tareas.add(new Tarea("3", "Adquisición de materiales y equipos", "Se deberán adquirir los materiales y equipos necesarios para la construcción del acueducto, garantizando la calidad y el cumplimiento de las especificaciones técnicas", EstadoTarea.EN_PROCESO, LocalDate.of(2022, 2, 16), 3));
		proyecto.setTareas(tareas);
		domain.getListaProyectos().add(proyecto);
		
		
		// Creacion de equipos
		Equipo equipo = new Equipo();
		equipo.setId(1);
		equipo.setNombre("Equipo 1");
		equipo.setEstado(EstadoEquipo.LIBRE);	
			ArrayList<Persona> integrantes = new ArrayList<>();
			integrantes.add(domain.getListaPersonas().get(0));
			integrantes.add(domain.getListaPersonas().get(1));		
		equipo.setIntegrantes(integrantes);
			ArrayList<Proyecto> proyectos = new ArrayList<>();
			proyectos.add(domain.getListaProyectos().get(0));
			proyectos.add(domain.getListaProyectos().get(1));
			proyectos.add(domain.getListaProyectos().get(2));
		equipo.setProyectos(proyectos);
		domain.getListaEquipos().add(equipo);
		
		equipo = new Equipo();
		equipo.setId(2);
		equipo.setNombre("Equipo 2");
		equipo.setEstado(EstadoEquipo.LIBRE);
			integrantes = new ArrayList<>();
			integrantes.add(domain.getListaPersonas().get(2));		
		equipo.setIntegrantes(integrantes);
			proyectos = new ArrayList<>();
			proyectos.add(domain.getListaProyectos().get(3));
			proyectos.add(domain.getListaProyectos().get(4));
		equipo.setProyectos(proyectos);
		domain.getListaEquipos().add(equipo);
		
		equipo = new Equipo();
		equipo.setId(3);
		equipo.setNombre("Equipo 3");
		equipo.setEstado(EstadoEquipo.OCUPADO);
			integrantes = new ArrayList<>();
// Recordar: Una persona solo puede pertenecer a un equipo			
//			integrantes.add(domain.getListaPersonas().get(2));
//			integrantes.add(domain.getListaPersonas().get(0));		
		equipo.setIntegrantes(integrantes);
			proyectos = new ArrayList<>();
			proyectos.add(domain.getListaProyectos().get(5));
		equipo.setProyectos(proyectos);
		domain.getListaEquipos().add(equipo);
		
	
		// Creacion de reuniones
		Reunion reunion = new Reunion();
		reunion.setId(1);
		reunion.setMotivo("Está Reunion se realiza porque tiririn tararan");
			ArrayList<Equipo> reu_int = new ArrayList<>();
			reu_int.add(domain.getListaEquipos().get(0));
			reu_int.add(domain.getListaEquipos().get(1));
		reunion.setIntegrantes(reu_int);
		domain.getListaReuniones().add(reunion);
		
		reunion = new Reunion();
		reunion.setId(2);
		reunion.setMotivo("Está Reunion se realiza porque ya tu sabe");
			reu_int = new ArrayList<>();
			reu_int.add(domain.getListaEquipos().get(1));
			reu_int.add(domain.getListaEquipos().get(2));
		reunion.setIntegrantes(reu_int);
		domain.getListaReuniones().add(reunion);
		
		
		System.out.println("---Datos Creados---");
	}
	
	
	public boolean verificarUsuario(String usuario, String contrasenia) {		
		return domain.verificarUsuario(usuario, contrasenia);
	}
	
	public ArrayList<Tarea> getListaTareasPorProyecto(int idProyecto) {
		return domain.getListaTareasPorProyecto(idProyecto);
	}
	
	public Proyecto getProyecto(int idProyecto) {
		return domain.getProyecto(idProyecto);
	}
	
	public boolean actualizarEstadoTarea(int idProyecto, String idTarea, EstadoTarea nuevoEstado){
		return domain.actualizarEstadoTarea(idProyecto, idTarea, nuevoEstado);
	}
	
	public Persona getPersonaPorId(int idPersona) {
		return domain.getPersonaPorId(idPersona);
	}
	
	public Persona getPersonaPorUsuarioYContrasenia(String user, String contrasenia) {
		return domain.getPersonaPorUsuarioYContrasenia(user, contrasenia);
	}
	
	public ArrayList<Proyecto> getProyectosPorPersona(int idPersona) {
		return domain.getProyectosPorPersona(idPersona);
	}
	
	public Equipo getEquipoPorPersona(int idPersona) {
		return domain.getEquipoPorPersona(idPersona);
	}
		
	public boolean agregarMensaje(int idPersona, Mensaje mensaje) {
		return domain.agregarMensaje(idPersona, mensaje);
	}

	
	public ArrayList<Documento> listaDocumentos = Domain.obtenerDocumentos();

	
	
	
	
	
	// PERSISTENCIA 
	
	public void cargarResourceBinario(){
		domain = Persistencia.cargarRecursoDomainBinario();
	}
	
	public void guardarResourceBinario(){
		Persistencia.guardarRecursoDomainBinario(domain);
	}
}
