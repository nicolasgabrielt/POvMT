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

import com.android.volley.Response.Listener;
import com.projectles.povmt.R;
import com.projectles.povmt.api.RestClient;
import com.projectles.povmt.api.shared.RequestManager;
import com.projectles.povmt.models.Atividade;
import com.projectles.povmt.models.TempoInvestido;
import com.projectles.povmt.models.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AtividadesDetalhadasActivity extends AppCompatActivity {

    private TextView nome;
    private TextView descricao;
    private TextView dataCriacao;
    private TextView tempoInvestido;
    private TextView dataAtualizacao;

    private RestClient client;
    private Atividade atividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = new RestClient(this);
        setContentView(R.layout.activity_atividades_detalhes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        atividade = (Atividade) i.getSerializableExtra("ATIVIDADE");

        nome = (TextView) findViewById(R.id.txt_atividade_nome);
        descricao = (TextView) findViewById(R.id.txt_descricao_atividade);
        dataAtualizacao = (TextView) findViewById(R.id.txt_data_update);
        dataCriacao = (TextView) findViewById(R.id.txt_data_criacao);
        tempoInvestido = (TextView) findViewById(R.id.txt_qnt_horas);

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

        // Specify an adapter (see also next example)
        nome.setText(atividade.getNome());
        descricao.setText(atividade.getDescricao());
        tempoInvestido.setText(String.valueOf(atividade.getTisum()));
        dataAtualizacao.setText(Util.getStringOfDateHoraDiaAno(atividade.getAtualizacao()));
        dataCriacao.setText(Util.getStringOfDateHoraDiaAno(atividade.getCriacao()));
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
                if (!edtTi.getText().toString().trim().isEmpty()) {
                    try {
                        Double.parseDouble(edtTi.getText().toString());
                        Map<String, String> params = new HashMap<>();
                        params.put("activityFk", atividade.getId().toString());
                        params.put("duration", edtTi.getText().toString());

                        if (Util.isConnectedToInternet(getApplicationContext())) {
                            client.tempoInvestido.addTempoInvestido(new Listener<TempoInvestido>() {
                                @Override
                                public void onResponse(TempoInvestido response) {
                                    atividade.addTempoInvestido(response);
                                    atividade.setAtualizacao(new Date());
                                    tempoInvestido.setText(String.valueOf(atividade.getTisum()));
                                    dataAtualizacao.setText(Util.getStringOfDateHoraDiaAno(atividade.getAtualizacao()));
                                }
                            }, params);
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Sem conexão com a internet, impossível completar a operação",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    } catch (NumberFormatException ex) {
                        Toast.makeText(getApplicationContext(),
                                "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
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
    public void onStop() {
        super.onStop();
        RequestManager.getInstance(this).stopRequestsInRequestQueueByTag(
                getString(R.string.activities_requests)
        );
    }
}
