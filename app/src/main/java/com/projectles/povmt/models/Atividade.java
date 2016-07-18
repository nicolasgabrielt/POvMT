package com.projectles.povmt.models;

import java.util.Date;

/**
 * Created by Nicolas on 17/07/2016.
 */
public class Atividade {
    private long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
