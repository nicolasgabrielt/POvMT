package com.projectles.povmt.api;


import android.content.Context;

import com.projectles.povmt.api.models.AtividadeRest;
import com.projectles.povmt.api.models.TempoInvestidoRest;

public class RestClient {

    private Context context;
    public final AtividadeRest atividades;
    public final TempoInvestidoRest tempoInvestido;

    public RestClient(Context context) {
        this.context = context;
        atividades = new AtividadeRest(context);
        tempoInvestido = new TempoInvestidoRest(context);
    }

    public Context getContext() {
        return context;
    }
}
