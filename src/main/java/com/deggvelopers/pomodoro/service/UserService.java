package com.deggvelopers.pomodoro.service;

import com.deggvelopers.pomodoro.entity.Configuration;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.exception.NotFoundException;
import com.deggvelopers.pomodoro.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

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

    private UserRepository userRepository;

    private ProjectService projectService;

    private ConfigurationService configService;

    public UserService(UserRepository userRepository, ProjectService projectService, ConfigurationService configService) {
        this.userRepository = userRepository;
        this.projectService = projectService;
        this.configService = configService;
    }

    @Transactional
    public User register(@Validated String name, @Validated String lastName, @Validated String mail, @Validated String password, @Validated String password2) throws NotFoundException {
        User user = new User();
        Configuration configuration = configService.crear();

        validacion(name, lastName, mail, password, password2);

        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(mail);
        user.setPassword(password);
        user.setPassword2(password2);

        String encrypted = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encrypted);
        user.setEnabled(Boolean.TRUE);
        user.setConfiguration(configuration);
        userRepository.save(user);

        projectService.create("Tareas", user);

        return user;
    }

    @Transactional
    public void update(String id, String name, String lastName, String email, String password, String password2) throws NotFoundException {
        validacion(name, lastName, email, password, password2);

        User user = findById(id);
        user.setLastName(lastName);
        user.setName(name);
        user.setEmail(email);

        if (password != null || !password.isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(password);
            user.setPassword(encryptedPassword);
        }

        userRepository.save(user);
    }

    private User findById(String id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el usuario solicitado."));
    }

    public void validacion(@Validated String nombre, @Validated String apellido, @Validated String mail, @Validated String password, @Validated String password2) throws NotFoundException {

        if (nombre == null || nombre.isEmpty() && !nombre.matches("^[a-zA-Z]*$")) {
            throw new NotFoundException("El nombre no puede estar vacio.");
        }

        if (apellido == null || apellido.isEmpty() && !apellido.matches("^[a-zA-Z]*$")) {
            throw new NotFoundException("El apellido no puede estar vacio.");
        }

        if (mail == null || mail.isEmpty() && !mail.matches("^[a-zA-Z]*$")) {
            throw new NotFoundException("Debe ingresar un mail valido.");
        }

        if (password == null || password.isEmpty() && !password.matches("^[a-zA-Z]*$")) {
            throw new NotFoundException("La contraseña no puede estar vacia.");
        }

        if (!password.equals(password2)) {
            throw new NotFoundException("Las claves deben ser iguales");
        }
    }

    public static boolean validacion(@Validated String datos) {
        return datos.matches("a-zA-Z*");
    }

    @Override
    public UserDetails loadUserByUsername(@Validated String email) throws UsernameNotFoundException {
        User usuario = userRepository.findByEmail(email);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("userSession", usuario);
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), permisos);
            return user;
        } else {
            return null;
        }
    }
}
