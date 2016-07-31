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

import java.util.Collections;
import java.util.List;

/**
 * Created by Nicolas on 17/07/2016.
 */
public class RelatorioSemanalAdapter extends RecyclerView.Adapter<RelatorioSemanalAdapter.ViewHolder> {


    private List<Atividade> mDataset;
    private Context context;
    private Activity activity;


    // Provide a suitable constructor (depends on the kind of dataset)
    public RelatorioSemanalAdapter(List<Atividade> myDataset , Activity context) {
        mDataset = myDataset;
        this.context = context;
        this.activity = context;
    }


    public void swap(List<Atividade> datas){
        mDataset.clear();
        Collections.sort(datas);
        Collections.reverse(datas);
        mDataset.addAll(datas);
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
    public RelatorioSemanalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_atividade, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Atividade atividade =  mDataset.get(position);

        holder.nomeAtividadeTxt.setText(atividade.getNome());
        holder.qntHorasTxt.setText( String.valueOf(atividade.getTempoTotalInvestidoSemanal(context)));
        holder.ultimaAtualizacao.setText(Util.getStringofDateDiaAno(atividade.getUltimaAtualizacao()));


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}