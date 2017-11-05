package goulart.message.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * Criado por Igor em 14/07/2017.
 */

public class GerenciaBD {

    @NonNull
    public synchronized static String inserirIdentificacao(Context context, String nick){
        // Objeto de acesso ao banco de dados
        SQLiteDatabase db;
        // Acesso ao banco de dados
        CriaBD banco = new CriaBD(context);

        // Criação de um mapa - chave valor para adição na tabela do banco de dados
        ContentValues valores;
        // Retorno da execução do insert
        long resultado;

        // Operação de escrita no banco
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        // Adição da coluna e o valor
        valores.put(CriaBD.SERVIDOR_NICK, nick);

        // Execução do insert no banco de dados na tabela servidor
        resultado = db.insert(CriaBD.TABELA_SERVIDOR, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public synchronized static String getIdentificacao(Context context){
        SQLiteDatabase db;
        CriaBD banco = new CriaBD(context);

        // Cursor que irá percorrer os resultados retornados da consulta
        Cursor cursor;

        // Colunas que o código deverá coletar
        String[] campos = {CriaBD.SERVIDOR_NICK};

        // Operação de leitura no banco
        db = banco.getReadableDatabase();
        // Passagem do nome da tabela e os campos
        /**
         * No db.query é possível passar um select,
         * adicionar um where
         * order by, entre outras opções sem gerar um código SQL
         */
        cursor = db.query(CriaBD.TABELA_SERVIDOR, campos, null, null, null, null, null, null);

        String retorno = "ERRO";
        // Um while percorrendo todos os registros
        while (cursor.moveToNext()){
            //cursor.getInt(indice - coluna que você deseja);
            //cursor.getDouble();
             retorno = cursor.getString(0);
        }
        db.close();

        return retorno;
    }

}
