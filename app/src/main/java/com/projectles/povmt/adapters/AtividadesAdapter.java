package com.projectles.povmt.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.projectles.povmt.R;
import com.projectles.povmt.activitys.AtividadesDetalhadasActivity;
import com.projectles.povmt.api.RestClient;
import com.projectles.povmt.models.TempoInvestido;
import com.projectles.povmt.models.Util;
import com.projectles.povmt.models.Atividade;

import java.util.Arrays;
import java.util.List;

public class AtividadesAdapter extends Adapter<AtividadesAdapter.ViewHolder> {

    private Context context;
    private RestClient client;
    private Activity activity;

    private List<Atividade> atividades;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AtividadesAdapter(List<Atividade> atividades , Context context) {
        this.context = context;
        this.atividades = atividades;
        this.activity = (Activity) context;
        this.client = new RestClient(context);
    }

    public void swap(List<Atividade> novasAtividades){
        atividades.clear();
        atividades.addAll(novasAtividades);
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeAtividadeTxt;
        public TextView qntHorasTxt;
        public TextView ultimaAtualizacao;

        public ViewHolder(View v) {
            super(v);
            nomeAtividadeTxt = (TextView) v.findViewById(R.id.nome_txt);
            qntHorasTxt = (TextView) v.findViewById(R.id.qnt_horas_txt);
            ultimaAtualizacao = (TextView) v.findViewById(R.id.txt_data_update);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AtividadesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_atividade, parent, false);

        // Set the view's size, margins, padding and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        // - Get element from your dataset at this position
        // - Replace the contents of the view with that element

        holder.nomeAtividadeTxt.setText(atividades.get(i).getNome());
        holder.qntHorasTxt.setText(String.valueOf(atividades.get(i).getTisum()));
        holder.ultimaAtualizacao.setText(Util.getStringOfDateDiaAno(atividades.get(i).getAtualizacao()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, AtividadesDetalhadasActivity.class);
                client.tempoInvestido.getTemposInvestidos(
                        String.valueOf(atividades.get(i).getId()),
                        new Listener<TempoInvestido[]>() {
                            @Override
                            public void onResponse(TempoInvestido[] response) {
                                atividades.get(i).setTis(Arrays.asList(response));
                                intent.putExtra("ATIVIDADE", atividades.get(i));
                                activity.startActivityForResult(intent, 1);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("RestError", "FAIL:, cause by: " + error.getCause());

                                intent.putExtra("ATIVIDADE", atividades.get(i));
                                activity.startActivityForResult(intent, 1);
                            }
                        });
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return atividades.size();
    }
}