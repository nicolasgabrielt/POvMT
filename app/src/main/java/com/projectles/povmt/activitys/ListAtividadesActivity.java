package com.projectles.povmt.activitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.projectles.povmt.DAO.AtividadeDAO;
import com.projectles.povmt.R;
import com.projectles.povmt.adapters.AtividadesAdapter;
import com.projectles.povmt.models.Atividade;

import java.util.Collections;
import java.util.List;

public class ListAtividadesActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private AtividadesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AtividadeDAO dao;
    private List<Atividade> atividades;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_atividades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_atividades);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogNewAtividade();
                mAdapter.notifyDataSetChanged();
            }
        });


        dao = new AtividadeDAO(getApplicationContext());
        atividades = dao.listaTodos();
        Collections.sort(atividades);
        Collections.reverse(atividades);
        mAdapter = new AtividadesAdapter(atividades, this);
        mRecyclerView.setAdapter(mAdapter);



    }


    public void createDialogNewAtividade() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.adicionar_atividade_dialog, null);
        builder.setView(dialogView);
        final EditText edtNome = (EditText) dialogView.findViewById(R.id.edtxt_nome);
        final EditText edtDescricao = (EditText) dialogView.findViewById(R.id.edtxt_descricao);

        // Add action buttons
        builder.setPositiveButton("Criar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String nome = edtNome.getText().toString();
                String descricao = edtDescricao.getText().toString();
                if(nome.trim().equals("") || descricao.trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Algum dos campos esta vazio!", Toast.LENGTH_LONG).show();
                } else {
                    Atividade atividade = new Atividade(edtNome.getText().toString(), edtDescricao.getText().toString());
                    dao.adiciona(atividade);
                    mAdapter.swap(dao.listaTodos());
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onRestart() {
        atividades = dao.listaTodos();
        mAdapter.swap(atividades);
        mAdapter.notifyDataSetChanged();
        Log.i("ENTROU","onRestart");
        super.onRestart();
    }


}
