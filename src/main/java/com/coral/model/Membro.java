package com.coral.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "membros")
public class Membro {

    public enum TipoFuncao {
        CORISTA,
        MUSICO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_funcao", nullable = false)
    private TipoFuncao tipoFuncao;

    @Column(name = "detalhe_funcao", length = 50)
    private String detalheFuncao;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Transient 
    private Presenca presencaTemporaria; 
    
    public Membro() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoFuncao getTipoFuncao() {
        return tipoFuncao;
    }

    public void setTipoFuncao(TipoFuncao tipoFuncao) {
        this.tipoFuncao = tipoFuncao;
    }

    public String getDetalheFuncao() {
        return detalheFuncao;
    }

    public void setDetalheFuncao(String detalheFuncao) {
        this.detalheFuncao = detalheFuncao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Presenca getPresencaTemporaria() {
        return presencaTemporaria;
    }

    public void setPresencaTemporaria(Presenca presencaTemporaria) {
        this.presencaTemporaria = presencaTemporaria;
    }
}