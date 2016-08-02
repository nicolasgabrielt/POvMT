package com.projectles.povmt.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.projectles.povmt.R;
import com.projectles.povmt.models.Util;

public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    private TextView loginPOVMT;
    private GoogleSignInOptions usuario;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usuario = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this , this).addApi(Auth.GOOGLE_SIGN_IN_API, usuario).build();
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.entrar).setOnClickListener(this);
        findViewById(R.id.desconetar).setOnClickListener(this);
        loginPOVMT = (TextView) findViewById(R.id.t);
        signIn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.entrar:
                if(!loginPOVMT.getText().equals("POVMT- Para Onde Vai Meu Tempo?"))
                    startActivity(new Intent(Login.this, ListarAtividadesActivity.class));
                else Toast.makeText(
                        getApplicationContext(),
                        "Sem usuário conectado",
                        Toast.LENGTH_LONG
                ).show();
                break;
            case R.id.desconetar:
                loginPOVMT.setText("POVMT- Para Onde Vai Meu Tempo?");
                revokeAccess();
                signOut();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess())
               recuperandoInformacoesUsuario(result.getSignInAccount());
        }
    }

    private void recuperandoInformacoesUsuario(GoogleSignInAccount acct){
        prefs = getSharedPreferences("POVMT_Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("nome",acct.getDisplayName());
        ed.putString("email",acct.getEmail());
        ed.putString("id",acct.getId());
        ed.commit();
        if (prefs.getString("nome","")!=null) loginPOVMT.setText("Bem vindo " + prefs.getString("nome",""));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        signIn();
    }

    private void signIn() {
        if (!Util.isConnectedToInternet(this)) {
            Toast.makeText(
                    getApplicationContext(),
                    "Sem conexão com a internet, impossível completar a operação",
                    Toast.LENGTH_LONG
            ).show();
        }else
            startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient), RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {@Override public void onResult(Status status) {}});
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {@Override public void onResult(Status status) {}});
    }
}
