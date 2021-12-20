package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.entidad.Prioridad;
import com.deggvelopers.pomodoro.entidad.Proyecto;
import com.deggvelopers.pomodoro.entidad.Tarea;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repositorio.ProyectoRepositorio;
import com.deggvelopers.pomodoro.repositorio.TareaRepositorio;
import com.deggvelopers.pomodoro.repositorio.UsuarioRepositorio;
import com.deggvelopers.pomodoro.servicio.TareaServicio;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tarea")
public class TareaControlador {

	@Autowired
	private TareaRepositorio tareaRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;

	@Autowired
	private TareaServicio tareaServicio;

	@Autowired
	private ProyectoRepositorio proyectoRepo;

	@GetMapping("/")
	public String todasTareasProyecto(@RequestParam String proyecto_id, ModelMap model) {

		List<Proyecto> proyectos = new ArrayList<>();
		proyectos.add(proyectoRepo.findById(proyecto_id).get());
		List<Tarea> tareas = tareaRepo.buscarPorProyecto(proyecto_id);

		model.put("proyectos", proyectos);
		model.put("tareas", tareas);

		return "tareas.html";
	}

	@GetMapping("/hoy")
	public String listarHoy(@RequestParam String usuario_id, ModelMap model) {

		Date hoy = new Date();
		List<Proyecto> proyectos = proyectoRepo.findByUserId(usuario_id);
		List<Tarea> tareas = tareaServicio.buscarTareasPorProyectos(proyectos, hoy);

		model.put("vista", "Hoy");
		model.put("proyectos", proyectos);
		model.put("tareas", tareas);

		return "tareas.html";
	}

	@GetMapping("/mañana")
	public String listarMañana(@RequestParam String usuario_id, ModelMap model) {

		Date mañana = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(mañana);
		c.add(Calendar.DATE, 1);
		mañana = c.getTime();

		List<Proyecto> proyectos = proyectoRepo.findByUserId(usuario_id);
		List<Tarea> tareas = tareaServicio.buscarTareasPorProyectos(proyectos, mañana);

		model.put("vista", "Mañana");
		model.put("proyectos", proyectos);
		model.put("tareas", tareas);

		return "tareas.html";
	}

	@GetMapping("/proximo")
	public String listarProximo(@RequestParam String usuario_id, ModelMap model) {

		Date proximo = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(proximo);
		c.add(Calendar.DATE, 1);
		proximo = c.getTime();

		List<Proyecto> proyectos = proyectoRepo.findByUserId(usuario_id);
		List<Tarea> tareas = tareaRepo.buscarPorProximo(proximo);

		model.put("vista", "Proximo");
		model.put("proyectos", proyectos);
		model.put("tareas", tareas);

		return "tareas.html";
	}

	@GetMapping("/completado")
	public String listarCompletado(ModelMap model) {

		List<Tarea> tareas = tareaRepo.buscarPorCompletado(Boolean.TRUE);
		model.put("tareas", tareas);

		return "tareas.html";
	}

	@PostMapping("/nueva")
	public String crearTarea(@RequestParam String vista,
			@RequestParam String nombre,
			@RequestParam String fecha,
			@RequestParam String proyecto_id,
			@RequestParam Prioridad prioridad,
			@RequestParam Integer cantidadPom,
			@RequestParam String usuario_id,
			ModelMap model) {

		try {
			String config_id = usuarioRepo.getById(usuario_id).getConfiguracion().getId();
			System.out.println("La fecha es " + fecha);
			int anio = Integer.parseInt(fecha.substring(0, 4));
			int mes = Integer.parseInt(fecha.substring(5, 7));
			int dia = Integer.parseInt(fecha.substring(8, 10));
			Date date = new Date(anio - 1900, mes - 1, dia);
			tareaServicio.crearTarea(nombre, date, proyecto_id, prioridad, cantidadPom, config_id);
			return "vistaPrincipal.html";
		} catch (ErrorServicio e) {
			model.put("error", e.getMessage());
			return "vistaPrincipal.html";
		}
	}

}
