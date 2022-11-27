package com.deggvelopers.pomodoro.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Project implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    @JoinColumn(referencedColumnName = "id")
    @ManyToOne
    private User user;

    public Project() {
    }

    public Project(String id, String nombre, User user) {
        this.id = id;
        this.nombre = nombre;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public User getUsuario() {
        return user;
    }

    public void setUsuario(User user) {
        this.user = user;
    }
}
