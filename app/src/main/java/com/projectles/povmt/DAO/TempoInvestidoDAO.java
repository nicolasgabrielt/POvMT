package com.projectles.povmt.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.projectles.povmt.models.TempoInvestido;
import com.projectles.povmt.models.Util;
import com.projectles.povmt.models.Atividade;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nicolas on 17/07/2016.
 */
public class TempoInvestidoDAO {


    private DBHelper dbHelper;
    private Context context;

    public static final String TABLE = "tempoInvestido";
    public static final String ID = "_id";
    public static final String ID_ATIVIDADE = "id_atividade";
    public static final String DATA = "dataCriacao";
    public static final String TEMPO_INVESTIDO = "tempoInvestido";

    public TempoInvestidoDAO(Context context) {

        dbHelper = new DBHelper(context);
        this.context = context;
    }


    /**
     * Adiciona objeto no banco de dados.
     */
    public void adiciona(TempoInvestido tempoInvestido) {
        // Encapsula no objeto do tipo ContentValues os valores a serem persistidos no banco de dados
        ContentValues values = new ContentValues();
        values.put(DATA, Util.getStringofDate(tempoInvestido.getData()));
        values.put(TEMPO_INVESTIDO,tempoInvestido.getTi());
        values.put(ID_ATIVIDADE, tempoInvestido.getId_atividade());

        // Instancia uma conexão com o banco de dados, em modo de gravação
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Insere o registro no banco de dados
        long id = db.insert(TABLE, null, values);
        tempoInvestido.setId(id);
        // Encerra a conexão com o banco de dados
        db.close();
}


    public  List<TempoInvestido> getTempoInvestidoByIdAtividade(Long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Executa a consulta no banco de dados
        List<TempoInvestido> temposInvestidos = new ArrayList<TempoInvestido>();
        Cursor c = db.rawQuery("select * from " + TABLE + " where " + ID_ATIVIDADE + "=" + id, null);


        /**
         * Percorre o Cursor, injetando os dados consultados em um objeto
         * do tipo ObjetoEmprestado e adicionando-os na List
         */
        try {
            while (c.moveToNext()) {

                TempoInvestido ti = new TempoInvestido();
                ti.setId(c.getLong(c.getColumnIndex(ID)));
                ti.setId_atividade(c.getLong(c.getColumnIndex(ID_ATIVIDADE)));
                ti.setData(Util.getDateofString(c.getString(c.getColumnIndex(DATA))));
                ti.setTi(c.getFloat(c.getColumnIndex(TEMPO_INVESTIDO)));

                temposInvestidos.add(ti);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            // Encerra o Cursor
            c.close();
        }
        // Encerra a conexão com o banco de dados
        db.close();

        return temposInvestidos;
    }





    /**
     * Lista todos os registros da tabela “objeto_emprestado”
     */
    public List<TempoInvestido> listaTodos() {
        // Cria um List para guardar os objetos consultados no banco de dados
        List<TempoInvestido> tempoInvestidos = new ArrayList<TempoInvestido>();
        // Instancia uma nova conexão com o banco de dados em modo leitura
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Executa a consulta no banco de dados
        Cursor c = db.query(TABLE, null, null, null, null,
                null, null);
        /**
         * Percorre o Cursor, injetando os dados consultados em um objeto
         * do tipo ObjetoEmprestado e adicionando-os na List
         */
        try {
            while (c.moveToNext()) {
                TempoInvestido ti = new TempoInvestido();
                ti.setId(c.getLong(c.getColumnIndex(ID)));
                ti.setId_atividade(c.getLong(c.getColumnIndex(ID_ATIVIDADE)));
                ti.setData(Util.getDateofString(c.getString(c.getColumnIndex(DATA))));
                ti.setTi(c.getFloat(c.getColumnIndex(TEMPO_INVESTIDO)));;

                tempoInvestidos.add(ti);
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
        return tempoInvestidos;
    }


}