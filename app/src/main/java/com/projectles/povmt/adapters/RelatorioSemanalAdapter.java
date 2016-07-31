package com.projectles.povmt.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projectles.povmt.R;
import com.projectles.povmt.models.Atividade;
import com.projectles.povmt.models.Util;

import java.util.List;

public class RelatorioSemanalAdapter extends RecyclerView.Adapter<RelatorioSemanalAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<Atividade> atividades;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RelatorioSemanalAdapter(List<Atividade> atividades , Context context) {
        this.atividades = atividades;

        this.context = context;
        this.activity = (Activity) context;
    }

    public void swap(List<Atividade> novasAtividades){
        atividades.clear();
        atividades.addAll(novasAtividades);
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item,
    // and you provide access to all the views for a data item in a view holder
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
    public RelatorioSemanalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_atividade, parent, false);
        // Set the view's size, margins, padding and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        // - Get element from your dataset at this position
        // - Replace the contents of the view with that element
        holder.nomeAtividadeTxt.setText(atividades.get(i).getNome());
        holder.qntHorasTxt.setText( String.valueOf(atividades.get(i).getTisum()));
        holder.ultimaAtualizacao.setText(Util.getStringOfDateDiaAno(atividades.get(i).getAtualizacao()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return atividades.size();
    }
}