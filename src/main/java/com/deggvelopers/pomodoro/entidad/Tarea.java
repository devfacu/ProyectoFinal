package com.deggvelopers.pomodoro.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
   @Temporal(TemporalType.DATE)
    
    private Date fecha;
    @JoinColumn(referencedColumnName = "id")
    @ManyToOne
    private Proyecto proyecto;
    private Prioridad prioridad;
    private Integer tiempoInvertido;
    private Boolean completado;
    private Integer cantidadPom;
    private Integer duracionPom;

    /// CONSTRUCTOR VACIO ///
    public Tarea() {
    }

    ////GETTERS Y SETTERS //////////
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getTiempoInvertido() {
        return tiempoInvertido;
    }

    public void setTiempoInvertido(Integer tiempoInvertido) {
        this.tiempoInvertido = tiempoInvertido;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public Integer getCantidadPom() {
        return cantidadPom;
    }

    public void setCantidadPom(Integer cantidadPom) {
        this.cantidadPom = cantidadPom;
    }

    public Integer getDuracionPom() {
        return duracionPom;
    }

    public void setDuracionPom(Integer duracionPom) {
        this.duracionPom = duracionPom;
    }

    /**
     * @return the proyecto
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

}
