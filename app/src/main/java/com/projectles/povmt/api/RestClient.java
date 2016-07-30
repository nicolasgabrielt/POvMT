package com.projectles.povmt.api;


import android.content.Context;

import com.projectles.povmt.api.models.AtividadeRest;

public class RestClient {

    private Context context;
    public final AtividadeRest atividades;

    public RestClient(Context context) {
        this.context = context;
        atividades = new AtividadeRest(context);
    }

    public Context getContext() {
        return context;
    }
}
