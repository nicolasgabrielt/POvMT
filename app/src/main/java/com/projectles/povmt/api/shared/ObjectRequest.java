package com.projectles.povmt.api.shared;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ObjectRequest<T> extends Request<T> {

    private Gson gson;
    private Class<T> clazz;
    private Map<String, String> params;
    private Listener<T> successResponse;

    private static final String DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public ObjectRequest(int method, String url, Class<T> clazz, Map<String, String> params,
                         Listener<T> successResponse, ErrorListener errorResponse) {

        super(method, url, errorResponse);

        this.clazz = clazz;
        this.params = params;
        this.successResponse = successResponse;
        gson = new GsonBuilder().setDateFormat(DATA_FORMAT).create();

    }

    public ObjectRequest(String url, Class<T> clazz, Map<String, String> params,
                         Listener<T> successResponse, ErrorListener errorResponse) {

        this(Method.GET, url, clazz, params, successResponse, errorResponse);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jBase = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers)
            );

            Log.i("Response", "Json: " + jBase);
            return Response.success(
                    gson.fromJson(jBase, clazz), HttpHeaderParser.parseCacheHeaders(response)
            );
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        successResponse.onResponse(response);
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }
}
