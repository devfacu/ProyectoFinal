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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String todasTareasProyecto(String attrPry_id, String proyecto_id, ModelMap model) {

		String pry_id;
		if (proyecto_id != null) {
			pry_id = proyecto_id;
		} else {
			pry_id = attrPry_id;
		}
		
		List<Proyecto> proyectos = new ArrayList<>();
		proyectos.add(proyectoRepo.findById(pry_id).get());
		List<Tarea> tareas = tareaRepo.buscarPorProyecto(pry_id);

		model.put("proyectos", proyectos);
		model.put("tareas", tareas);

		return "tareas.html";
	}

	@GetMapping("/hoy")
	public String listarHoy(@ModelAttribute String attrUsr_id, String usuario_id, ModelMap model) {

		String usr_id;
		if (usuario_id != null) {
			usr_id = usuario_id;
		} else {
			usr_id = attrUsr_id;
		}
		Date hoy = new Date();
		List<Proyecto> proyectos = proyectoRepo.findByUserId(usr_id);
		List<Tarea> tareas = tareaServicio.buscarTareasPorProyectos(proyectos, hoy);

		model.put("vista", "Hoy");
		model.put("proyectos", proyectos);
		model.put("tareas", tareas);

		return "tareas.html";
	}

	@GetMapping("/manana")
	public String listarMañana(@ModelAttribute String attrUsr_id, String usuario_id, ModelMap model) {

		String usr_id;
		if (usuario_id != null) {
			usr_id = usuario_id;
		} else {
			usr_id = attrUsr_id;
		}

		Date mañana = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(mañana);
		c.add(Calendar.DATE, 1);
		mañana = c.getTime();

		List<Proyecto> proyectos = proyectoRepo.findByUserId(usr_id);
		List<Tarea> tareas = tareaServicio.buscarTareasPorProyectos(proyectos, mañana);

		model.put("vista", "Mañana");
		model.put("proyectos", proyectos);
		model.put("tareas", tareas);

		return "tareas.html";
	}

	@GetMapping("/proximo")
	public String listarProximo(@ModelAttribute String attrUsr_id, String usuario_id, ModelMap model) {

		String usr_id;
		if (usuario_id != null) {
			usr_id = usuario_id;
		} else {
			usr_id = attrUsr_id;
		}

		Date proximo = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(proximo);
		c.add(Calendar.DATE, 1);
		proximo = c.getTime();

		List<Proyecto> proyectos = proyectoRepo.findByUserId(usr_id);
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
			ModelMap model,
			RedirectAttributes attr) {

		vista = vistaChk(vista);
		
		try {
			String config_id = usuarioRepo.getById(usuario_id).getConfiguracion().getId();
			System.out.println("La fecha es " + fecha);
			int anio = Integer.parseInt(fecha.substring(0, 4));
			int mes = Integer.parseInt(fecha.substring(5, 7));
			int dia = Integer.parseInt(fecha.substring(8, 10));
			Date date = new Date(anio - 1900, mes - 1, dia);
			tareaServicio.crearTarea(nombre, date, proyecto_id, prioridad, cantidadPom, config_id);
			attr.addAttribute("usuario_id", usuario_id);
			attr.addAttribute("attrPry_id", proyecto_id);
			return "redirect:/tarea/" + vista;

		} catch (ErrorServicio e) {

			model.put("error", e.getMessage());
			attr.addAttribute("attrUsr_id", usuario_id);
			return "redirect:/tarea/" + vista;
		}
	}

	@PostMapping("/eliminar")
	public String eliminar(ModelMap model, RedirectAttributes attr, @RequestParam String tarea_id, @RequestParam String vista, String usuario_id, String proyecto_id) {
		
		vista = vistaChk(vista);
		
		try {
			System.out.println("El proyecto: " + proyecto_id);
			model.put("vista", vista);
			attr.addAttribute("attrUsr_id", usuario_id);
			attr.addAttribute("attrPry_id", proyecto_id);
			tareaServicio.eliminarT(tarea_id);
			return "redirect:/tarea/";
		} catch (ErrorServicio ex) {
			model.put("error", ex.getMessage());
			return "redirect:/tarea/" + vista;
		}

	}
	
	private String vistaChk(String vista) {
		if (null == vista) {
			return "";
		}else switch (vista) {
			case "Mañana":
				return "manana";
			default:
				return vista.toLowerCase();
		}
	}
	

}
