package com.projectles.povmt.models;

import java.io.Serializable;
import java.util.Date;

public class TempoInvestido implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long atividade;
    private Date data;
    private double ti;

    public TempoInvestido(double ti, Long atividade){
        this.ti = ti;
        this.atividade = atividade;
        data = new Date();
    }

    public TempoInvestido(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAtividade() {
        return atividade;
    }

    public double getTi() {
        return ti;
    }

    public void setTi(float ti) {
        this.ti = ti;
    }

    public Date getCriacao() {
        return data;
    }

    public void setCriacao(Date criacao) {
        this.data = criacao;
    }
}
