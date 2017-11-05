package goulart.message.cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

import goulart.message.conversa.MensagemRecebida;
import goulart.message.conversa.NovaMensagemServidor;

/**
 * Created by Igor on 15/10/2017.
 */

public class Recebedor implements Runnable {

    /**
     * Este InputStream é a mensagem do servidor
     */
    private InputStream servidor;

    private NovaMensagemServidor novaMensagemServidor;

    public Recebedor(InputStream servidor, NovaMensagemServidor novaMensagemServidor) {
        this.servidor = servidor;
        this.novaMensagemServidor = novaMensagemServidor;
    }

    public void run() {
        /**
         * Nosso código ficará travado nesta parte
         *
         * Quando o servidor enviar uma mensagem iremos enviá-la para a tela de mensagens
         *
         * Para enviar para a tela de mensagens iremos utilizar novaMensagemServidor
         */
        Scanner s = new Scanner(this.servidor);
        while (s.hasNextLine()) {
            String mensagem = s.nextLine();
            try{
                JSONObject jsonObject = new JSONObject(mensagem);
                MensagemRecebida mensagemRecebida =
                        new MensagemRecebida(jsonObject.getString("nick"),
                                jsonObject.getString("conteudo"));
                novaMensagemServidor.novaMensagem(mensagemRecebida);
            } catch (JSONException e) {
                System.out.println("ERRO DE JSON (RECEBEDOR): " + e.getMessage());
            }
        }
    }
}
