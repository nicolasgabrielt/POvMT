package com.projectles.povmt.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projectles.povmt.DAO.AtividadeDAO;
import com.projectles.povmt.DAO.TempoInvestidoDAO;
import com.projectles.povmt.R;
import com.projectles.povmt.models.Atividade;
import com.projectles.povmt.models.TempoInvestido;
import com.projectles.povmt.models.Util;

import java.io.Serializable;
import java.util.Date;

public class atividadesDetalhesActivity extends AppCompatActivity {
    private Atividade atividade;
    private AtividadeDAO dao;
    private TempoInvestidoDAO daoTempo;
    private TextView descricaoAtividade;
    private TextView nomeAtividade;
    private TextView dataUpdate;
    private TextView dataCriacao;
    private TextView tempoEstimado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades_detalhes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        atividade = (Atividade) i.getSerializableExtra("ATIVIDADE");

        nomeAtividade = (TextView) findViewById(R.id.txt_atividade_nome);
        descricaoAtividade = (TextView) findViewById(R.id.txt_descricao_atividade);
        dataUpdate = (TextView) findViewById(R.id.txt_data_update);
        dataCriacao = (TextView) findViewById(R.id.txt_data_criacao);
        tempoEstimado = (TextView) findViewById(R.id.txt_qnt_horas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_ti);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createDialogNewTi();
            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();
        // specify an adapter (see also next example)
        dao = new AtividadeDAO(getApplicationContext());
        daoTempo = new TempoInvestidoDAO(getApplicationContext());
        nomeAtividade.setText(atividade.getNome());
        descricaoAtividade.setText(atividade.getDescricao());
        tempoEstimado.setText(String.valueOf(atividade.getTempoTotalInvestido(getApplicationContext())));
        dataUpdate.setText(Util.getStringofDateHoraDiaAno(atividade.getUltimaAtualizacao()));
        dataCriacao.setText(Util.getStringofDateHoraDiaAno(atividade.getDataCriacao()));

    }




    public void createDialogNewTi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.adicionar_ti_dialog, null);
        builder.setView(dialogView);
        final EditText edtTi = (EditText) dialogView.findViewById(R.id.edtxt_qnt_horas);

        // Add action buttons
        builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (!(edtTi.getText().equals(null)) && !(edtTi.getText().toString().trim().equals("")) ){
                    float ti = (float) Double.parseDouble(edtTi.getText().toString());
                    TempoInvestido tempo = new TempoInvestido(ti,atividade.getId());
                    atividade.addTempoInvestido(getApplicationContext(),tempo);
                    atividade.setUltimaAtualizacao(new Date());
                    dao.atualiza(atividade);
                    tempoEstimado.setText(String.valueOf(atividade.getTempoTotalInvestido(getApplicationContext())));
                    dataUpdate.setText(Util.getStringofDateHoraDiaAno(atividade.getUltimaAtualizacao()));
                }else{
                    Toast.makeText(getApplicationContext(),"Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
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


}
