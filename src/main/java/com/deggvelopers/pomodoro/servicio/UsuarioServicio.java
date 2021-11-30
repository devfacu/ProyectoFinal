package com.deggvelopers.pomodoro.servicio;

import com.deggvelopers.pomodoro.entidad.Proyecto;
import com.deggvelopers.pomodoro.entidad.Usuario;
import com.deggvelopers.pomodoro.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UsuarioServicio  {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Usuario registrarUsuario(@Validated String nombre, String apellido, String mail, String password) throws Exception {
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setPassword(password);

        if (nombre == null || nombre.isEmpty() && !nombre.matches("^[a-zA-Z]*$")) {
            throw new Exception("Al registrarse le falto completar algun campo");
        }

        if (apellido == null || nombre.isEmpty() && !apellido.matches("^[a-zA-Z]*$")) {
            throw new Exception("Al registrarse le falto completar algun campo");
        }

        if (mail == null || nombre.isEmpty() && !mail.matches("^[a-zA-Z]*$")) {
            throw new Exception("Al registrarse le falto completar algun campo");
        }

        if (password == null || nombre.isEmpty() && !password.matches("^[a-zA-Z]*$")) {
            throw new Exception("Al registrarse le falto completar algun campo");
        }

//        Proyecto proyecto = new Proyecto();
//
//        proyecto.setNombre(nombre);
//        proyecto.setUsuario(usuario);

        return usuario;
    }
    
 

//    public Proyecto nuevoProyecto (Usuario usuario) {
//        Proyecto proyecto = new Proyecto();
//
//        proyecto.setNombre(nombre);
//        proyecto.setUsuario(usuario);
//
//        return proyecto;
//    }

    public static boolean validacion(String datos) {
        return datos.matches("a-zA-Z*");
    }
}
