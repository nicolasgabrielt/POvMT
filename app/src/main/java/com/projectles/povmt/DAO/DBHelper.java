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
    public static final String DATA = "dataCriacao";
    public static final String TEMPO_INVESTIDO = "tempoInvestido";
    public static final String ULTIMA_ATUALIZACAO = "ultimaAtualizacao";

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

        String sql = "CREATE TABLE atividade(" +
                ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                ","+NOME +" TEXT NOT NULL" +
                ",descricao TEXT NOT NULL" +
                ","+ ULTIMA_ATUALIZACAO +" DATETIME NOT NULL" +
                ","+ DATA +" DATETIME NOT NULL" +
                ");";
        db.execSQL(sql);


        String sql1 = "CREATE TABLE tempoInvestido(" +
                ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                ","+ TEMPO_INVESTIDO +" REAL NOT NULL" +
                ","+ DATA +" DATETIME NOT NULL" +","
                + "id_atividade" + " integer,"
                + " FOREIGN KEY ("+"id_atividade"+") REFERENCES "+ "atividade" +"("+ID+"));";
        db.execSQL(sql1);

        Atividade[] atividades = new Atividade[]{null,null,null};

        Calendar c = Calendar.getInstance();
        Atividade atividade = new Atividade("Cálculo 1", "bacana");
        ContentValues values = new ContentValues();
        values.put("nome", atividade.getNome());
        values.put("descricao", atividade.getDescricao());
        values.put("dataCriacao", Util.getStringofDate(atividade.getDataCriacao()));
        values.put("ultimaAtualizacao", Util.getStringofDate(atividade.getUltimaAtualizacao()));
        long id = db.insert("atividade", null, values);
        atividade.setId(id);
        atividades[0] = atividade;

        atividade = new Atividade("Física 1", "bacana");
        values.clear();
        values.put("nome", atividade.getNome());
        values.put("descricao", atividade.getDescricao());
        values.put("dataCriacao", Util.getStringofDate(atividade.getDataCriacao()));
        values.put("ultimaAtualizacao", Util.getStringofDate(atividade.getUltimaAtualizacao()));
        id = db.insert("atividade", null, values);
        atividade.setId(id);
        atividades[1] = atividade;

        atividade = new Atividade("História 1", "bacana");
        values.clear();
        values.put("nome", atividade.getNome());
        values.put("descricao", atividade.getDescricao());
        values.put("dataCriacao", Util.getStringofDate(atividade.getDataCriacao()));
        values.put("ultimaAtualizacao", Util.getStringofDate(atividade.getUltimaAtualizacao()));
        id = db.insert("atividade", null, values);
        atividade.setId(id);
        atividades[2] = atividade;

        Random r = new Random();

        for(int i=1; i <= 30; i++ ) {
            TempoInvestido ti = new TempoInvestido(r.nextFloat()*5f, atividades[i%3].getId());
            ti.setData(c.getTime());
            values.clear();
            values.put("dataCriacao", Util.getStringofDate(ti.getData()));
            values.put("tempoInvestido",ti.getTi());
            values.put("id_atividade", ti.getId_atividade());
            id = db.insert("tempoInvestido", null, values);
            ti.setId(id);
            c.add(Calendar.DATE,-1);
        }
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
