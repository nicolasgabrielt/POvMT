package com.projectles.povmt.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.projectles.povmt.R;
import com.projectles.povmt.adapters.AtividadesAdapter;
import com.projectles.povmt.api.RestClient;
import com.projectles.povmt.api.shared.RequestManager;
import com.projectles.povmt.models.Atividade;
import com.projectles.povmt.models.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RelatorioSemanalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AtividadesAdapter adapter;

    private RestClient client;

    private List<Atividade> atividades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = new RestClient(this);
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

        // Adding adapter to recyclerView
        adapter = new AtividadesAdapter(atividades, this);
        recyclerView.setAdapter(adapter);

        if (Util.isConnectedToInternet(this)) {
            client.atividades.getAtividades(new Listener<Atividade[]>() {
                @Override
                public void onResponse(Atividade[] response) {
                    atividades.addAll(Arrays.asList(response));
                    Collections.sort(atividades, Collections.<Atividade>reverseOrder());

                    adapter = new AtividadesAdapter(atividades, client.getContext());
                    recyclerView.setAdapter(adapter);
                }
            }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("RestError", "Fail cause by: " + error.getCause());
                }
            });
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Sem conexão com a internet, impossível completar a operação",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        RequestManager.getInstance(this).stopRequestsInRequestQueueByTag(
                getString(R.string.activities_requests)
        );
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }


}
