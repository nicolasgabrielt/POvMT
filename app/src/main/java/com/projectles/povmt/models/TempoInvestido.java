package com.projectles.povmt.models;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nicolas on 26/07/2016.
 */
public class TempoInvestido implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long id_atividade;
    private Date data;
    private float ti;

    public TempoInvestido(float ti, Long id_atividade){
        this.ti = ti;
        this.id_atividade = id_atividade;
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

    public Long getId_atividade() {
        return id_atividade;
    }

    public void setId_atividade(Long id_atividade) {
        this.id_atividade = id_atividade;
    }

    public float getTi() {
        return ti;
    }

    public void setTi(float ti) {
        this.ti = ti;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
