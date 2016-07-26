package com.projectles.povmt.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projectles.povmt.DAO.AtividadeDAO;
import com.projectles.povmt.R;
import com.projectles.povmt.activitys.atividadesDetalhesActivity;
import com.projectles.povmt.models.Atividade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Nicolas on 17/07/2016.
 */
public class AtividadesAdapter extends RecyclerView.Adapter<AtividadesAdapter.ViewHolder> {


    private List<Atividade> mDataset;
    private Context context;
    private Activity activity;


    // Provide a suitable constructor (depends on the kind of dataset)
    public AtividadesAdapter(List<Atividade> myDataset , Activity context) {
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
    public AtividadesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        holder.qntHorasTxt.setText( String.valueOf(atividade.getTempoInvestido()));
        holder.ultimaAtualizacao.setText(getStringofDate(atividade.getUltimaAtualizacao()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, atividadesDetalhesActivity.class);
                intent.putExtra("ATIVIDADE", atividade);
                activity.startActivityForResult(intent, 1);

            }
        });

    }

    private String getStringofDate(Date date){
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");//dd/MM/yyyy
        String strDate = sdfDate.format(date);
        return strDate;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}