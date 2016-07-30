package com.projectles.povmt.api.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.projectles.povmt.R;
import com.projectles.povmt.api.shared.ObjectRequest;
import com.projectles.povmt.api.shared.RequestManager;
import com.projectles.povmt.models.Atividade;

import java.util.Map;

public class AtividadeRest {

    private Context context;

    public AtividadeRest(Context context) {
        this.context = context;
    }

    public void getAtividades(Listener<Atividade[]> successResponse) {
        ObjectRequest<Atividade[]> objRequest = new ObjectRequest<>(
                context.getString(R.string.url_atividades),
                Atividade[].class,
                null,
                successResponse,
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        );
        objRequest.setTag(
                context.getString(R.string.activities_requests)
        );
        RequestManager.getInstance(context).addToRequestQueue(objRequest);
    }

    public void addAtividade(Listener<Atividade[]> successResponse, Map<String, String> params) {
        ObjectRequest<Atividade> objRequest = new ObjectRequest<Atividade>(
                Method.POST,
                context.getString(R.string.url_atividades),
                Atividade.class,
                params,
                new Listener<Atividade>() {
                    @Override
                    public void onResponse(Atividade response) {
                        Log.i("Rest", "SUCCESS: " + response);
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        );
        objRequest.setTag(
                context.getString(R.string.activities_requests)
        );
        RequestManager.getInstance(context).addToRequestQueue(objRequest);
        getAtividades(successResponse);
    }
}
