package com.projectles.povmt.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projectles.povmt.R;
import com.projectles.povmt.models.Atividade;


/**
 * Created by Nicolas on 20/07/2016.
 */
public class DialogAtividadeAdapter extends BaseAdapter {
    private View gridView;
    private final Activity mActivity;
    private LayoutInflater mInflater;
    private final Context mContext;
    private List<Atividade> itens;

    public DialogAtividadeAdapter (Activity activity, List<Atividade> itens) {
        mActivity = activity;
        mContext = activity;

        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(activity);
    }

    //Retorna a quantidade de Itens
    @Override
    public int getCount() {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public Atividade getItem(int position) {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) ((Activity) mContext)
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridView = new View(mContext);

            // get layout
            gridView = inflater.inflate(R.layout.item_atividade_dialog, null);

            Atividade atividade = getItem(position);
            // set value into textview

            TextView nomeAtividadeTxt = (TextView) gridView.findViewById(R.id.nome_txt);
            TextView  qntHorasTxt = (TextView) gridView.findViewById(R.id.qnt_horas_txt);
            TextView  ultimaAtualizacao = (TextView) gridView.findViewById(R.id.txt_data_update);

            nomeAtividadeTxt.setText(atividade.getNome());
            qntHorasTxt.setText( String.valueOf(atividade.getTempoInvestido()));
            ultimaAtualizacao.setText(getStringofDate(atividade.getUltimaAtualizacao()));


//
//            final ImageView btnFavorite = (ImageView)  gridView.findViewById(R.id.btn_favorite);
//
//            btnFavorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    btnFavorite.setBackgroundResource(R.mipmap.ic_star_border_yellow);
//                }
//            });


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    private String getStringofDate(Date date){
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");//dd/MM/yyyy
        String strDate = sdfDate.format(date);
        return strDate;
    }


}

