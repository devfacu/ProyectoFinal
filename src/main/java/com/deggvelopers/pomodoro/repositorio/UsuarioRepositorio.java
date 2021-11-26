/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deggvelopers.pomodoro.repositorio;

import com.deggvelopers.pomodoro.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Administrador
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
    
    
    
}
