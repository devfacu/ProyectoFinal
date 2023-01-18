package com.deggvelopers.pomodoro.service;

import com.deggvelopers.pomodoro.dto.user.CreateUserRequest;
import com.deggvelopers.pomodoro.dto.user.UpdateUserRequest;
import com.deggvelopers.pomodoro.dto.user.UserResponse;
import com.deggvelopers.pomodoro.entity.Configuration;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.exception.NotFoundException;
import com.deggvelopers.pomodoro.mapper.IUserMapper;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private ProjectService projectService;

    private ConfigurationService configService;

    private IUserMapper userMapper;

    public UserService(UserRepository userRepository, ProjectService projectService, ConfigurationService configService,
                       IUserMapper userMapper) {
        this.userRepository = userRepository;
        this.projectService = projectService;
        this.configService = configService;
        this.userMapper = userMapper;
    }

    @Transactional
    public User register(CreateUserRequest createUserRequest) throws NotFoundException {
        User user = new User();
        Configuration configuration = configService.crear();

        validacion(createUserRequest);

        user.setName(user.getName());
        user.setLastName(createUserRequest.getLastName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        user.setPassword2(createUserRequest.getPassword2());

        String encrypted = new BCryptPasswordEncoder().encode(createUserRequest.getPassword());
        user.setPassword(encrypted);
        user.setEnabled(Boolean.TRUE);
        user.setConfiguration(configuration);
        userRepository.save(user);

        projectService.create("Tareas", user);

        return user;
    }

    @Transactional
    public UserResponse update(UpdateUserRequest updateUserRequest) throws NotFoundException {

        User user = findById(updateUserRequest.getId());
        user.setLastName(updateUserRequest.getLastName());
        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());

        String password = updateUserRequest.getPassword();
        if (password != null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(password);
            user.setPassword(encryptedPassword);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    private User findById(String id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el usuario solicitado."));
    }

    public void validacion(CreateUserRequest createUserRequest) throws NotFoundException {

        if (createUserRequest.getName() == null || createUserRequest.getName().isEmpty() && !createUserRequest.getName().matches("^[a-zA-Z]*$")) {
            throw new NotFoundException("El nombre no puede estar vacio.");
        }

        if (createUserRequest.getLastName() == null || createUserRequest.getLastName().isEmpty() && !createUserRequest.getLastName().matches("^[a-zA-Z]*$")) {
            throw new NotFoundException("El apellido no puede estar vacio.");
        }

        if (createUserRequest.getEmail() == null || createUserRequest.getEmail().isEmpty() && !createUserRequest.getEmail().matches("^[a-zA-Z]*$")) {
            throw new NotFoundException("Debe ingresar un mail valido.");
        }

        if (createUserRequest.getPassword() == null || createUserRequest.getPassword().isEmpty() && !createUserRequest.getPassword().matches("^[a-zA-Z]*$")) {
            throw new NotFoundException("La contraseña no puede estar vacia.");
        }

        if (!createUserRequest.getPassword().equals(createUserRequest.getPassword2())) {
            throw new NotFoundException("Las claves deben ser iguales");
        }
    }

    public static boolean validacion(String datos) {
        return datos.matches("a-zA-Z*");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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
