package com.projectles.povmt.activitys;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.projectles.povmt.DAO.AtividadeDAO;
import com.projectles.povmt.R;
import com.projectles.povmt.adapters.AtividadesAdapter;
import com.projectles.povmt.models.Atividade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class ListAtividades extends AppCompatActivity {


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

        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.btn_add);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                Log.i("FOI", "NAOENTROU");
                int id = menuItem.getItemId();
                if (id == R.id.action_atividade) {
                    Log.i("FOI", "ENTROU");
                    onCreateDialog();
                    mAdapter.notifyDataSetChanged();

                }
                return true;
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        // specify an adapter (see also next example)
        dao = new AtividadeDAO(getApplicationContext());
        List<Atividade> myDataset = dao.listaTodos();
        Collections.reverse(myDataset);
        mAdapter = new AtividadesAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.adicionar_atividade_fragment, null);
        builder.setView(dialogView);
        final EditText edtNome = (EditText) dialogView.findViewById(R.id.edtxt_nome);
        final EditText edtDescricao = (EditText) dialogView.findViewById(R.id.edtxt_descricao);

        // Add action buttons
        builder.setPositiveButton("Criar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Atividade atividade = new Atividade(edtNome.getText().toString(), edtDescricao.getText().toString());
                dao.adiciona(atividade);
                mAdapter.swap(dao.listaTodos());
                Toast.makeText(getApplicationContext(), "Objeto salvo com sucesso! " + "Numero de Elementos : " + dao.listaTodos().size() , Toast.LENGTH_LONG)
                        .show();



            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
