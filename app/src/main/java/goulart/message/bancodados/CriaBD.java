package goulart.message.bancodados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Criado por Igor em 14/07/2017.
 */

public class CriaBD extends SQLiteOpenHelper {

    /*NOME DO BANCO DE DADOS*/
    private static final String NOME_BANCO = "bancoAndroidCS.db";

    /*TABELA SERVIDOR*/
    public static final String TABELA_SERVIDOR = "servidor";
    public static final String SERVIDOR_NICK = "nick";

    /*VERSÃO DO BANCO DE DADOS*/
    private static final int VERSAO = 1;

    /*
     * O context permite acessar recursos presentes no aplicativo
     * Com ele é possível trocar a tela ou iniciar um serviço
     *
     * Uma activity, fragment e serviço são contextos que podem utilizar os recursos da aplicação
     *
     * Eles podem compartilhar este contexto
     * */

    public CriaBD(Context context) {
        /*
        * Chamada ao construtor superior passando um:
        * - contexto
        * - o nome do banco
        * - versão
        * */
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        * Este método será executado caso o banco sofra alguma alteração ou não esteja criado
        * */
        String sql = "CREATE TABLE " + TABELA_SERVIDOR + " ("
                + SERVIDOR_NICK + " text"
                +")";
        /*
        * execSQL -> executa o seu comando ***SQL***
        * */
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        * Ao atulizar o banco você pode realizar alguma operação no banco de dados
        *
        * Neste exemplo estou eliminando a tabela servidor e recriando ela depois no método
        * on create
        * ***SÓ SERÁ EXECUTADO SE A VERSÃO ALTERAR***
        * */
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_SERVIDOR);
    }

}
