package com.projectles.povmt.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.db.chart.view.BarChartView;
import com.projectles.povmt.DAO.AtividadeDAO;
import com.projectles.povmt.R;
import com.projectles.povmt.models.Atividade;

import java.util.List;

public class Relatorio extends AppCompatActivity {

    private AtividadeDAO dao;
    private List<Atividade> atividades;
    private BarChartView semana_atual, semana_passada, semana_retrasada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dao = new AtividadeDAO(getApplicationContext());
        atividades = dao.listaTodos();

        semana_atual = (BarChartView) findViewById(R.id.graph_curr_week);
        semana_passada = (BarChartView) findViewById(R.id.graph_last_week);
        semana_retrasada = (BarChartView) findViewById(R.id.graph_two_weeks);


    }

}
