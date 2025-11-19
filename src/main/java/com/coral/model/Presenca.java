package com.coral.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "presencas")
public class Presenca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne 
    @JoinColumn(name = "id_membro", nullable = false) 
    private Membro membro;

    @ManyToOne // Muitas Presenças para um Evento
    @JoinColumn(name = "id_evento", nullable = false) 
    private Evento evento;

    @Column(name = "presente")
    private Boolean presente = false;

    public Presenca() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }
}