package com.deggvelopers.pomodoro.service;

import com.deggvelopers.pomodoro.entity.Configuration;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Validated
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ConfigurationService configService;

    @Transactional
    public User registrar(@Validated String name, @Validated String lastName, @Validated String mail, @Validated String password, @Validated String password2) throws ErrorServicio {
        User user = new User();
        Configuration configuration = configService.crear();

        validacion(name, lastName, mail, password, password2);

        user.setName(name);
        user.setLastName(lastName);
        user.setMail(mail);
        user.setPassword(password);
        user.setPassword2(password2);

        String encrypted = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encrypted);
        user.setEnabled(Boolean.TRUE);
        user.setConfiguration(configuration);
        userRepository.save(user);

        projectService.crearProyecto("Tareas", user);

        return user;
    }

    @Transactional
    public void modificar(String id, String nombre, String apellido, String email, String password, String password2) throws ErrorServicio {
        validacion(nombre, apellido, email, password, password2);

        Optional<User> respuesta = userRepository.findById(id);
        if (respuesta.isPresent()) {
            User user = respuesta.get();
            user.setLastName(apellido);
            user.setName(nombre);
            user.setMail(email);

            if (password != null || !password.isEmpty()) {
                String encriptada = new BCryptPasswordEncoder().encode(password);
                user.setPassword(encriptada);
            }

            userRepository.save(user);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    public void validacion(@Validated String nombre, @Validated String apellido, @Validated String mail, @Validated String password, @Validated String password2) throws ErrorServicio {

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

        if (!password.equals(password2)) {
            throw new ErrorServicio("Las claves deben ser iguales");
        }
    }

    public static boolean validacion(@Validated String datos) {
        return datos.matches("a-zA-Z*");
    }

    @Override
    public UserDetails loadUserByUsername(@Validated String email) throws UsernameNotFoundException {
        User usuario = userRepository.buscarPorMail(email);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuarioSession", usuario);
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(usuario.getMail(), usuario.getPassword(), permisos);
            return user;
        } else {
            return null;
        }
    }
}
