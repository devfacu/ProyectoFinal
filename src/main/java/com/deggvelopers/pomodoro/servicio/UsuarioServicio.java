package com.deggvelopers.pomodoro.servicio;

import com.deggvelopers.pomodoro.entidad.Usuario;
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
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ProyectoServicio proyectoServicio; 

    @Transactional
    public Usuario registrar(@Validated String nombre, String apellido, String mail, String password) throws Exception {
        Usuario usuario = new Usuario();

        validacion(nombre, apellido, mail, password); 

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setPassword(password);
        usuarioRepositorio.save(usuario);
        
        proyectoServicio.crearProyecto("Tareas", usuario); 
        
        return usuario;
    }

    public void validacion(String nombre, String apellido, String mail, String password) throws Exception {

        if (nombre == null || nombre.isEmpty() && !nombre.matches("^[a-zA-Z]*$")) {
            throw new Exception("El nombre no puede estar vacio.");
        }

        if (apellido == null || apellido.isEmpty() && !apellido.matches("^[a-zA-Z]*$")) {
            throw new Exception("El apellido no puede estar vacio.");
        }

        if (mail == null || mail.isEmpty() && !mail.matches("^[a-zA-Z]*$")) {
            throw new Exception("Debe ingresar un mail valido.");
        }

        if (password == null || password.isEmpty() && !password.matches("^[a-zA-Z]*$")) {
            throw new Exception("La contraseña no puede estar vacia.");
        }
    }

    public static boolean validacion(String datos) {
        return datos.matches("a-zA-Z*");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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
