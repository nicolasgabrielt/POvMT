package com.projectles.povmt.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.projectles.povmt.DAO.AtividadeDAO;
import com.projectles.povmt.DAO.TempoInvestidoDAO;
import com.projectles.povmt.R;
import com.projectles.povmt.adapters.AtividadesAdapter;
import com.projectles.povmt.models.Atividade;

import java.util.Collections;
import java.util.List;

public class RelatorioSemanalActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AtividadesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AtividadeDAO dao;
    private List<Atividade> atividades;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_semanal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mRecyclerView = (RecyclerView) findViewById(R.id.list_atividades);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        dao = new AtividadeDAO(getApplicationContext());
        atividades = dao.listaTodos();
        Collections.sort(atividades);
        Collections.reverse(atividades);
        mAdapter = new AtividadesAdapter(atividades, this);
        mRecyclerView.setAdapter(mAdapter);
    }

}
