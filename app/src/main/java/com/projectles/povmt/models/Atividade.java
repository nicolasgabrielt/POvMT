package com.projectles.povmt.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nicolas on 17/07/2016.
 */
public class Atividade implements Comparable<Atividade> ,Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private float tempoInvestido;
    private String nome;
    private Date ultimaAtualizacao;
    private Date dataCriacao;
    private String descricao;

    public Atividade(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.ultimaAtualizacao = new Date();
        this.dataCriacao = new Date();
        this.tempoInvestido = 0;
    }


    public Atividade() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getTempoInvestido() {
        return tempoInvestido;
    }

    public void setTempoInvestido(float tempoInvestido) {
        this.tempoInvestido = tempoInvestido;

    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }


    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    @Override
    public int compareTo(Atividade o) {
        if (getUltimaAtualizacao() == null || o.getUltimaAtualizacao() == null)
            return 0;
        return getUltimaAtualizacao().compareTo(o.getUltimaAtualizacao());
    }
}