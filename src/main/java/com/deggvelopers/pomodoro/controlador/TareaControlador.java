package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.entidad.Proyecto;
import com.deggvelopers.pomodoro.entidad.Tarea;
import com.deggvelopers.pomodoro.repositorio.ProyectoRepositorio;
import com.deggvelopers.pomodoro.repositorio.TareaRepositorio;
import com.deggvelopers.pomodoro.servicio.TareaServicio;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tarea")
public class TareaControlador {

    @Autowired
    private TareaRepositorio tareaRepo;
	
	@Autowired
	private TareaServicio tareaServicio;
	
	@Autowired
	private ProyectoRepositorio proyectoRepo;

    @GetMapping("/hoy")
    public String listarHoy(ModelMap model, @RequestParam String usuario_id) {
		
        Date hoy = new Date();
        List<Proyecto> proyectos = proyectoRepo.findByUserId("usuario_id");
        List<Tarea> tareas = tareaServicio.buscarTareasPorProyectos(proyectos, hoy);
        model.put("tareas", tareas);

        return "tareas.html";
    }
    
    @GetMapping("/mañana")
    public String listarMañana(ModelMap model) {

        Date mañana = new Date();
        
        Calendar c = Calendar.getInstance(); 
        c.setTime(mañana);
        c.add(Calendar.DATE, 1);
        mañana = c.getTime(); 
        
        List<Proyecto> proyectos = proyectoRepo.findByUserId("usuario_id");
        List<Tarea> tareas = tareaServicio.buscarTareasPorProyectos(proyectos, mañana);
        
        model.put("tareas", tareas);                

        return "tareas.html";
    } 
    
    @GetMapping("/proximo")
    public String listarProximo(ModelMap model) {

        Date proximo = new Date();
        
        Calendar c = Calendar.getInstance(); 
        c.setTime(proximo);
        c.add(Calendar.DATE, 1);
        proximo = c.getTime(); 
        
        List<Tarea> tareas = tareaRepo.buscarPorProximo(proximo);
        model.put("tareas", tareas);                

        return "tareas.html";
    } 
	
	@GetMapping("/completado")
    public String listarCompletado(ModelMap model) {

        List<Tarea> tareas = tareaRepo.buscarPorCompletado(Boolean.TRUE);
        model.put("tareas", tareas);

        return "tareas.html";
    }
}
