package com.projectles.povmt.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.projectles.povmt.models.Atividade;

/**
 * Created by Nicolas on 17/07/2016.
 */
public class DBHelper  extends SQLiteOpenHelper {


    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String DATA = "dataCriacao";
    public static final String TEMPO_INVESTIDO = "tempoInvestido";
    public static final String ULTIMA_ATUALIZACAO = "ultimaAtualizacao";

    // Nome do banco de dados
    private static final String NOME_DO_BANCO = "dados";
    // Versão atual do banco de dados
    private static final int VERSAO_DO_BANCO = 1;
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

        String sql = "CREATE TABLE atividade(" +
                ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                ","+NOME +" TEXT NOT NULL" +
                ",descricao TEXT NOT NULL" +
                ","+ ULTIMA_ATUALIZACAO +" TEXT NOT NULL" +
                ","+ DATA +" TEXT NOT NULL" +
                ","+ TEMPO_INVESTIDO +" REAL NOT NULL" +
                ");";
        db.execSQL(sql);
        Atividade atividade = new Atividade("Calculo 1", "bacana");
        db.execSQL(createInsertAtividadesQuery(atividade.getNome(),atividade.getDescricao(),String.valueOf(atividade.getUltimaAtualizacao()),String.valueOf(atividade.getDataCriacao()),atividade.getTempoInvestido()));


    }

    /**
     * Atualiza a estrutura da tabela no banco de dados, caso sua versão tenha mudado.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS atividade;";
        db.execSQL(sql);
        onCreate(db);
    }




    private static String createInsertAtividadesQuery(String nome, String descricao ,String dataCriacao, String dataUpdate, float tempoInvestido) {
        return "INSERT INTO atividade (nome,descricao,ultimaAtualizacao,dataCriacao,tempoInvestido) VALUES('" + nome + "','" + descricao + "','" + dataUpdate + "','" + dataCriacao + "','" + tempoInvestido + "');";
    }


}
