package com.projectles.povmt.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.projectles.povmt.models.Util;
import com.projectles.povmt.models.Atividade;


/**
 * Created by Nicolas on 17/07/2016.
 */
public class AtividadeDAO {


    private DBHelper dbHelper;
    private Context context;

    public static final String TABLE = "atividade";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String DATA = "dataCriacao";
    public static final String TEMPO_INVESTIDO = "tempoInvestido";
    public static final String ULTIMA_ATUALIZACAO = "ultimaAtualizacao";

    public AtividadeDAO(Context context) {

        dbHelper = new DBHelper(context);
        this.context = context;
    }


    /**
     * Adiciona objeto no banco de dados.
     */
    public void adiciona(Atividade atividade) {
        // Encapsula no objeto do tipo ContentValues os valores a serem persistidos no banco de dados
        ContentValues values = new ContentValues();
        values.put(NOME, atividade.getNome());
        values.put("descricao", atividade.getDescricao());
        values.put(DATA, Util.getStringofDate(atividade.getDataCriacao()));
        values.put(ULTIMA_ATUALIZACAO, Util.getStringofDate(atividade.getUltimaAtualizacao()));

        // Instancia uma conexão com o banco de dados, em modo de gravação
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Insere o registro no banco de dados
        long id = db.insert(TABLE, null, values);
        atividade.setId(id);
        // Encerra a conexão com o banco de dados
        db.close();
}


    /**
     * Altera o registro no banco de dados.
     */
    public void atualiza(Atividade atividade) {
        // Encapsula no objeto do tipo ContentValues os valores a serem atualizados no banco de dados
        ContentValues values = new ContentValues();
        values.put(NOME, atividade.getNome());
        values.put("descricao", atividade.getDescricao());
        values.put(DATA, Util.getStringofDate(atividade.getDataCriacao()));
        values.put(ULTIMA_ATUALIZACAO, Util.getStringofDate(atividade.getUltimaAtualizacao()));


        // Instancia uma conexão com o banco de dados, em modo de gravação
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Atualiza o registro no banco de dados
        db.update(TABLE, values, "_id=?", new String[]{String.valueOf(atividade.getId())});
        // Encerra a conexão com o banco de dados
        db.close();
    }

    public Atividade getAtividadeById(Long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Executa a consulta no banco de dados
        Cursor c = db.rawQuery("select * from " + TABLE + " where " + ID + "=" + id, null);


        /**
         * Percorre o Cursor, injetando os dados consultados em um objeto
         * do tipo ObjetoEmprestado e adicionando-os na List
         */
        try {
            while (c.moveToNext()) {

                Atividade atividade = new Atividade();
                atividade.setId(c.getLong(c.getColumnIndex(ID)));
                atividade.setNome(c.getString(c.getColumnIndex(NOME)));
                atividade.setDescricao(c.getString(c.getColumnIndex("descricao")));
                atividade.setDataCriacao(Util.getDateofString(c.getString(c.getColumnIndex(DATA))));
                atividade.setUltimaAtualizacao(Util.getDateofString(c.getString(c.getColumnIndex(ULTIMA_ATUALIZACAO))));

                return atividade;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            // Encerra o Cursor
            c.close();
        }
        // Encerra a conexão com o banco de dados
        db.close();

        return null;
    }


    /**
     * Lista todos os registros da tabela “objeto_emprestado”
     */
    public List<Atividade> listaTodos() {
        // Cria um List para guardar os objetos consultados no banco de dados
        List<Atividade> atividades = new ArrayList<Atividade>();
        // Instancia uma nova conexão com o banco de dados em modo leitura
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Executa a consulta no banco de dados
        Cursor c = db.query("atividade", null, null, null, null,
                null, null);
        /**
         * Percorre o Cursor, injetando os dados consultados em um objeto
         * do tipo ObjetoEmprestado e adicionando-os na List
         */
        try {
            while (c.moveToNext()) {
                Atividade atividade = new Atividade();
                atividade.setId(c.getLong(c.getColumnIndex(ID)));
                atividade.setNome(c.getString(c.getColumnIndex(NOME)));
                atividade.setDescricao(c.getString(c.getColumnIndex("descricao")));
                atividade.setDataCriacao(Util.getDateofString(c.getString(c.getColumnIndex(DATA))));
                atividade.setUltimaAtualizacao(Util.getDateofString(c.getString(c.getColumnIndex(ULTIMA_ATUALIZACAO))));
                atividades.add(atividade);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            // Encerra o Cursor
            c.close();
        }
        // Encerra a conexão com o banco de dados
        db.close();
        // Retorna uma lista com os objetos consultados
        return atividades;
    }


}