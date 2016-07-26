package com.projectles.povmt.models;

import android.content.Context;

import com.projectles.povmt.DAO.TempoInvestidoDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

/**
 * Created by Nicolas on 17/07/2016.
 */
public class Atividade implements Comparable<Atividade> , Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private List<TempoInvestido> temposInvestidos;
    private Date ultimaAtualizacao;
    private Date dataCriacao;
    private String descricao;

    public Atividade(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.ultimaAtualizacao = new Date();
        this.dataCriacao = new Date();
        this.temposInvestidos = new ArrayList<TempoInvestido>();
    }


    public Atividade() {
        this.temposInvestidos = new ArrayList<TempoInvestido>();
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

    public List<TempoInvestido> getTemposInvestidos(Context context) {
        TempoInvestidoDAO dao = new TempoInvestidoDAO(context);
        this.temposInvestidos = dao.getTempoInvestidoByIdAtividade(id);
        return temposInvestidos;
    }


    public void setTemposInvestidos(List<TempoInvestido> temposInvestidos) {
        this.temposInvestidos = temposInvestidos;
    }

    public void addTempoInvestido(Context context,TempoInvestido tempo){
        TempoInvestidoDAO dao = new TempoInvestidoDAO(context);
        dao.adiciona(tempo);
    }


    public float getTempoTotalInvestido(Context context) {
        float ti = 0;
        for (int i = 0; i < getTemposInvestidos(context).size(); i++) {
             ti += getTemposInvestidos(context).get(i).getTi();
        }
        return ti;
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