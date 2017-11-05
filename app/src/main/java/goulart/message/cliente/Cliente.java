package goulart.message.cliente;

import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import goulart.message.conversa.NovaMensagemServidor;

/**
 * Created by Igor on 15/10/2017.
 */

public class Cliente {

    /**
     * Iremos precisar do IP do servidor e a porta para realizar a conexão
     *
     * Vamos criar um PrintStream para enviar uma mensagem pro servidor
     *
     * Vamos receber uma instância de NovaMensagemServidor
     * Esta instância será utilizada para exibir as mensagens na tela do usuário
     */
    private String IP_SERVIDOR;
    private int PORTA;
    private Socket cliente;
    private PrintStream envio;
    private NovaMensagemServidor novaMensagemServidor;

    public Cliente (String IP_SERVIDOR, int PORTA, NovaMensagemServidor novaMensagemServidor) {
        this.IP_SERVIDOR = IP_SERVIDOR;
        this.PORTA = PORTA;
        this.novaMensagemServidor = novaMensagemServidor;
    }

    public boolean conectarServidor(){
        try {
            /**
             * Vamos nos conectar ao servidor
             *
             * Vamos habilitar também um recebedor
             *
             * Este recebedor irá ficar escutando as respostas do servidor
             */
            cliente = new Socket(this.IP_SERVIDOR, this.PORTA);
            envio = new PrintStream(cliente.getOutputStream());
            habilitarRecebedor();
            return true;
        } catch (IOException e) {
            Log.d("CLIENTE", "ERRO AO CRIAR O CLIENTE (CLIENTE): " + e.getMessage());
        }
        return false;
    }

    private void habilitarRecebedor(){
        try {
            /**
             * Vamos criar a classe Recebedor
             */
            Recebedor recebedor = new Recebedor(cliente.getInputStream(), novaMensagemServidor);
            new Thread(recebedor).start();
        } catch (IOException e) {
            Log.d("CLIENTE", "EERO AO HABILITAR O RECEBEDOR (CLIENTE)");
        }
    }

    /**
     * Para enviar uma mensagem pro servidor iremos utilizar o println
     *
     * Este método irá enviar a mensagem pro servidor
     */
    public void enviarMensagem(String mensagem) {
        envio.println(mensagem);
    }

    /**
     * Vamos encerrar a conexão com o servidor simplesmente dando um close na conexão
     */
    public void encerrarConexao(){
        try {
            if(cliente != null){
                cliente.close();
                envio.close();
            }
        } catch (IOException e) {
            Log.d("CLIENTE", "ERRO AO FECHAR A CONEXÃO COM O SERVIDOR - CLIENTE");
        }
    }

}
