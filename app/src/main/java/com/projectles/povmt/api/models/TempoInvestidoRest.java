package com.projectles.povmt.api.models;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.projectles.povmt.R;
import com.projectles.povmt.api.shared.ObjectRequest;
import com.projectles.povmt.api.shared.RequestManager;;
import com.projectles.povmt.models.TempoInvestido;

import java.util.Map;

public class TempoInvestidoRest {

    private Context context;

    public TempoInvestidoRest(Context context) {
        this.context = context;
    }

    public void addTempoInvestido(Listener<TempoInvestido> successResponse,
                                  Map<String, String> params) {
        ObjectRequest<TempoInvestido> objRequest = new ObjectRequest<>(
                Request.Method.POST,
                context.getString(R.string.base_url_ti),
                TempoInvestido.class,
                params,
                successResponse,
                new Response.ErrorListener() {
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

    public void getTemposInvestidos(String atividadeId, Listener<TempoInvestido[]> successResponse) {
        this.getTemposInvestidos(atividadeId, successResponse,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void getTemposInvestidos(String atividadeId, Listener<TempoInvestido[]> successResponse,
                                    ErrorListener errorResponse) {
        ObjectRequest<TempoInvestido[]> objRequest = new ObjectRequest<>(
                context.getString(R.string.base_url_ti) + atividadeId,
                TempoInvestido[].class,
                null,
                successResponse,
                errorResponse
        );
        objRequest.setTag(
                context.getString(R.string.activities_requests)
        );
        RequestManager.getInstance(context).addToRequestQueue(objRequest);
    }
}
