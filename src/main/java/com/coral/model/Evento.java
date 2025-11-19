package com.coral.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {

    public enum TipoEvento {
        ENSAIO,
        APRESENTACAO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoEvento tipo;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "local", length = 150)
    private String local;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    public Evento() {}
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoEvento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEvento tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}