package com.projectles.povmt.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.projectles.povmt.models.Atividade;
import com.projectles.povmt.models.TempoInvestido;
import com.projectles.povmt.models.Util;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by Nicolas on 17/07/2016.
 */
public class DBHelper  extends SQLiteOpenHelper {


    public static final String ID = "_id";
    public static final String NOME = "nome";


    // Nome do banco de dados
    private static final String NOME_DO_BANCO = "dados";
    // Versão atual do banco de dados
    private static final int VERSAO_DO_BANCO = 3;
    private static Context mContext;

    public DBHelper(Context context) {

        super(context, NOME_DO_BANCO, null, VERSAO_DO_BANCO);
        mContext = context;
    }

    /**
     * Cria a tabela no banco de dados, caso ela não exista.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE usuario(" +
                ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                ","+NOME +" TEXT NOT NULL" +
                ",senha TEXT NOT NULL" +
                ");";
        db.execSQL(sql);
    }

    /**
     * Atualiza a estrutura da tabela no banco de dados, caso sua versão tenha mudado.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS atividade;";
        db.execSQL(sql);
        String sql1 = "DROP TABLE IF EXISTS tempoInvestido;";
        db.execSQL(sql1);
        onCreate(db);
    }




    private static String createInsertAtividadesQuery(String nome, String descricao ,String dataCriacao, String dataUpdate, float tempoInvestido) {
        return "INSERT INTO atividade (nome,descricao,ultimaAtualizacao,dataCriacao,tempoInvestido) VALUES('" + nome + "','" + descricao + "','" + dataUpdate + "','" + dataCriacao + "','" + tempoInvestido + "');";
    }


}
