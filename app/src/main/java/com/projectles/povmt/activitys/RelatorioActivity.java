package com.projectles.povmt.activitys;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.ChartView;
import com.projectles.povmt.R;
import com.projectles.povmt.api.RestClient;
import com.projectles.povmt.models.Atividade;
import com.projectles.povmt.models.TempoInvestido;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RelatorioActivity extends AppCompatActivity {

    private static final int DIVISOES_Y = 4;

    private RestClient client;
    private List<Atividade> atividades;

    private BarSet barSetCurr, barSetLast, barSetLate;
    private BarChartView currWeek, lastWeek, lateWeek;
    private int[] cores = new int[]{4620980, 11674146, 25600, 10506797, 12092939, 8421504, 12357519};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        atividades = new ArrayList<>();;
        client = new RestClient(getApplicationContext());

        client.atividades.getAtividades(new Listener<Atividade[]>() {
            @Override
            public void onResponse(Atividade[] response) {
                atividades.addAll(Arrays.asList(response));

            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        geraBarras(atividades);
        currWeek = (BarChartView) findViewById(R.id.graph_curr_wk);
        lastWeek = (BarChartView) findViewById(R.id.graph_last_wk);
        lateWeek = (BarChartView) findViewById(R.id.graph_two_wks);
        setGraph();
    }

    private void setGraph(){
        int interval, maxInterval;
        double max = Math.max(maximo(barSetCurr), maximo(barSetLast));

        max = Math.max(max, maximo(barSetLate));
        interval = (int) Math.ceil(max / DIVISOES_Y);
        maxInterval = interval * DIVISOES_Y;

        Paint paint = new Paint();

        if (barSetCurr.size() > 0) {
            currWeek.setAxisBorderValues(0, maxInterval, interval);
            currWeek.setGrid(ChartView.GridType.HORIZONTAL,DIVISOES_Y, 1, paint);
            currWeek.addData(barSetCurr);
            currWeek.show();
        }
        if (barSetLast.size() > 0) {
            lastWeek.setAxisBorderValues(0, maxInterval, interval);
            lastWeek.setGrid(ChartView.GridType.HORIZONTAL,DIVISOES_Y, 1, paint);
            lastWeek.addData(barSetLast);
            lastWeek.show();
        }
        if (barSetLate.size() > 0) {
            lateWeek.setAxisBorderValues(0, maxInterval, interval);
            lateWeek.setGrid(ChartView.GridType.HORIZONTAL,DIVISOES_Y, 1, paint);
            lateWeek.addData(barSetLate);
            lateWeek.show();
        }
    }

    private double maximo(BarSet barras){
        double max = 0;
        float atual;
        for (int i =0; i < barras.size();i++){
            atual = barras.getValue(i);
            if(atual > max){
                max = atual;
            }
        }
        return max;
    }

    private void geraBarras(List<Atividade> atividades){
        Date data;
        Atividade atividade;
        float tiSemAtual, tiSemPassada, tiSemRetrasada;

        Calendar semAtual = Calendar.getInstance();
        semAtual.add(Calendar.DATE, -7);

        Calendar semPassada = Calendar.getInstance();
        semPassada.add(Calendar.DATE, -14);

        Calendar semRetrasada = Calendar.getInstance();
        semRetrasada.add(Calendar.DATE, -21);

        barSetCurr = new BarSet();
        barSetLast = new BarSet();
        barSetLate = new BarSet();

        for (int i=0; i < atividades.size(); i++){
            atividade = atividades.get(i);
            tiSemAtual = tiSemPassada = tiSemRetrasada = 0;
            for(TempoInvestido ti : atividade.getTis()) {
                data = ti.getCriacao();
                if (data.after(semAtual.getTime())) {
                    tiSemAtual += ti.getTi();
                }
                else if (data.after(semPassada.getTime())) {
                    tiSemPassada += ti.getTi();
                }
                else if (data.after(semRetrasada.getTime())) {
                    tiSemRetrasada += ti.getTi();
                }
            }
            if (tiSemAtual != 0){
                Bar b = new Bar(atividade.getNome(), tiSemAtual);
                b.setColor(cores[barSetCurr.size() % 7]);
                barSetCurr.addBar(b);
            }
            if (tiSemPassada != 0){
                Bar b = new Bar(atividade.getNome(),tiSemPassada);
                b.setColor(cores[barSetLast.size() % 7]);
                barSetLast.addBar(b);
            }
            if (tiSemRetrasada != 0){
                Bar b = new Bar(atividade.getNome(),tiSemRetrasada);
                b.setColor(cores[barSetLate.size() % 7]);
                barSetLate.addBar(b);
            }
        }
    }
}
