package com.projectles.povmt.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.projectles.povmt.R;

public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    private TextView tt;
    private GoogleSignInOptions usuario;
    public GoogleSignInAccount acct;
    public String nomeUser;
    public String emailUser;
    public String idUser;
    public Uri fotoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usuario = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this , this).addApi(Auth.GOOGLE_SIGN_IN_API, usuario).build();
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.entrar).setOnClickListener(this);
        findViewById(R.id.desconetar).setOnClickListener(this);
        tt = (TextView) findViewById(R.id.t);
        signIn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.entrar:
                if(!tt.getText().equals("POVMT- Para Onde Vai Meu Tempo?"))
                    startActivity(new Intent(Login.this, ListarAtividadesActivity.class));
                break;
            case R.id.desconetar:
                tt.setText("POVMT- Para Onde Vai Meu Tempo?");
                signOut();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {@Override public void onResult(Status status) {}});
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        signIn();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()){          //recuperando informações do usuário
                acct = result.getSignInAccount();
                nomeUser = acct.getDisplayName();
                emailUser = acct.getEmail();
                idUser = acct.getId();
                fotoUser = acct.getPhotoUrl();
                if (usuario != null) tt.setText("Bem vindo " + nomeUser);
            } //else signIn();
        }
    }
}
