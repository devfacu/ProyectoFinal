package com.deggvelopers.pomodoro.servicio;

import com.deggvelopers.pomodoro.entidad.Configuracion;
import com.deggvelopers.pomodoro.entidad.Usuario;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
	public Usuario registrar(@Validated String nombre, @Validated String apellido, @Validated String mail, @Validated String password) throws ErrorServicio {
		Usuario usuario = new Usuario();
		Configuracion configuracion = configServicio.crear();

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

	@Transactional
	public void modificar(String id, String nombre, String apellido, String email, String password) throws ErrorServicio {
		validacion(nombre, apellido, email, password);

		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			usuario.setApellido(apellido);
			usuario.setNombre(nombre);
			usuario.setMail(email);

			if (password != null || !password.isEmpty()) {
				String encriptada = new BCryptPasswordEncoder().encode(password);
				usuario.setPassword(encriptada);
			}

			usuarioRepositorio.save(usuario);
		} else {
			throw new ErrorServicio("No se encontro el usuario solicitado");
		}
	}

	public void validacion(@Validated String nombre, @Validated String apellido, @Validated String mail, @Validated String password) throws ErrorServicio {

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

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuarioSession", usuario);
			User user = new User(usuario.getMail(), usuario.getPassword(), permisos);
			return user;
		} else {
			return null;
		}
	}
}
