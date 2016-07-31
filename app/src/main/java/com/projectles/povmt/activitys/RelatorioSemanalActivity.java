package com.projectles.povmt.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.projectles.povmt.R;
import com.projectles.povmt.adapters.AtividadesAdapter;
import com.projectles.povmt.api.RestClient;
import com.projectles.povmt.models.Atividade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RelatorioSemanalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AtividadesAdapter adapter;

    private RestClient client;
    private List<Atividade> atividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_semanal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.list_atividades);
        // Use this setting to improve performance if you know that changes in content do not change
        // the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        client = new RestClient(this);
        client.atividades.getAtividades(new Response.Listener<Atividade[]>() {
            @Override
            public void onResponse(Atividade[] response) {
                atividades = new ArrayList<>();
                atividades.addAll(Arrays.asList(response));

                Collections.sort(atividades, Collections.<Atividade>reverseOrder());
                adapter = new AtividadesAdapter(atividades, client.getContext());
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                adapter = new AtividadesAdapter(new ArrayList<Atividade>(), client.getContext());
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
