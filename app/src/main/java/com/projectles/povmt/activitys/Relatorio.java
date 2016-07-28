package com.projectles.povmt.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.projectles.povmt.DAO.AtividadeDAO;
import com.projectles.povmt.R;
import com.projectles.povmt.models.Atividade;
import com.projectles.povmt.models.TempoInvestido;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Relatorio extends AppCompatActivity {

    private AtividadeDAO dao;
    private List<Atividade> atividades;
    private BarChartView semana_atual, semana_passada, semana_retrasada;
    private BarSet barras_atual, barras_passada, barras_retrasada;
    private int[] cores = new int[]{4620980,11674146,25600,10506797,12092939,8421504,12357519};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dao = new AtividadeDAO(getApplicationContext());
        atividades = dao.listaTodos();

        geraBarras(atividades);

        semana_atual = (BarChartView) findViewById(R.id.graph_curr_wk);
        semana_passada = (BarChartView) findViewById(R.id.graph_last_wk);
        semana_retrasada = (BarChartView) findViewById(R.id.graph_two_wks);

        if(barras_atual.size() > 0) {
            semana_atual.addData(barras_atual);
            semana_atual.show();
        }
        if(barras_passada.size() > 0) {
            semana_passada.addData(barras_passada);
            semana_passada.show();
        }
        if(barras_retrasada.size() > 0) {
            semana_retrasada.addData(barras_retrasada);
            semana_retrasada.show();
        }
    }

    private void geraBarras(List<Atividade> atividades){
        Atividade atv;
        Date data;
        Calendar sem_at = Calendar.getInstance();
        sem_at.add(Calendar.DATE,-7);
        Calendar sem_pas = Calendar.getInstance();
        sem_pas.add(Calendar.DATE,-14);
        Calendar sem_ret = Calendar.getInstance();
        sem_ret.add(Calendar.DATE,-21);
        float ti_sem_at, ti_sem_ps, ti_sem_rt;

        barras_atual = new BarSet();
        barras_passada = new BarSet();
        barras_retrasada = new BarSet();

        for(int i=0; i < atividades.size(); i++){
            atv = atividades.get(i);
            ti_sem_at = 0;
            ti_sem_ps = 0;
            ti_sem_rt = 0;
            for(TempoInvestido ti : atv.getTemposInvestidos(getApplicationContext())) {
                data = ti.getData();
                if (data.after(sem_at.getTime())) {
                    ti_sem_at += ti.getTi();
                }
                else if (data.after(sem_pas.getTime())) {
                    ti_sem_ps += ti.getTi();
                }
                else if (data.after(sem_ret.getTime())) {
                    ti_sem_rt += ti.getTi();
                }
            }
            if(ti_sem_at != 0){
                Bar b = new Bar(atv.getNome(),ti_sem_at);
                b.setColor(cores[barras_atual.size()%7]);
                barras_atual.addBar(b);
            }
            if(ti_sem_ps != 0){
                Bar b = new Bar(atv.getNome(),ti_sem_at);
                b.setColor(cores[barras_passada.size()%7]);
                barras_passada.addBar(b);
            }
            if(ti_sem_rt != 0){
                Bar b = new Bar(atv.getNome(),ti_sem_at);
                b.setColor(cores[barras_retrasada.size()%7]);
                barras_retrasada.addBar(b);
            }
        }
    }
}
