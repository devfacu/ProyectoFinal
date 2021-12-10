package com.deggvelopers.pomodoro.servicio;

import com.deggvelopers.pomodoro.entidad.Configuracion;
import com.deggvelopers.pomodoro.entidad.Usuario;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ProyectoServicio proyectoServicio;
    
    @Autowired
    private ConfiguracionServicio configServicio; 

    @Transactional
    public Usuario registrar(@Validated String nombre, @Validated String apellido, @Validated String mail,  @Validated String password) throws ErrorServicio {
        Usuario usuario = new Usuario();
        Configuracion configuracion = new Configuracion();
        configServicio.crear(); 

        validacion(nombre, apellido, mail, password);

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setPassword(password);

        String encriptada = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(encriptada);
        usuario.setHabilitado(Boolean.TRUE);
        usuario.setConfiguracion(configuracion);
        usuarioRepositorio.save(usuario);
        
        proyectoServicio.crearProyecto("Tareas", usuario);          

        return usuario;
    }

    public void validacion(@Validated String nombre, @Validated String apellido,@Validated String mail,@Validated String password) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty() && !nombre.matches("^[a-zA-Z]*$")) {
            throw new ErrorServicio("El nombre no puede estar vacio.");
        }

        if (apellido == null || apellido.isEmpty() && !apellido.matches("^[a-zA-Z]*$")) {
            throw new ErrorServicio("El apellido no puede estar vacio.");
        }

        if (mail == null || mail.isEmpty() && !mail.matches("^[a-zA-Z]*$")) {
            throw new ErrorServicio("Debe ingresar un mail valido.");
        }

        if (password == null || password.isEmpty() && !password.matches("^[a-zA-Z]*$")) {
            throw new ErrorServicio("La contraseña no puede estar vacia.");
        }
    }

    public static boolean validacion(@Validated String datos) {
        return datos.matches("a-zA-Z*");
    }

    @Override
    public UserDetails loadUserByUsername(@Validated String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMail(email);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);

//			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//			HttpSession session = attr.getRequest().getSession(true);
//			session.setAttribute("usuarioSession", usuario);
            User user = new User(usuario.getMail(), usuario.getPassword(), permisos);
            return user;
        } else {
            return null;
        }
    }
}
