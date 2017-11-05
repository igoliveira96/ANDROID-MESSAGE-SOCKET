package goulart.message.servidores;

import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 15/10/2017.
 */

public class ServidorTCP {

    private int PORTA;
    /**
     * PrintStream será o canal de resposta ao cliente
     *
     * Vamos criar uma lista  de canais (clientes)
     */
    private List<PrintStream> clientes;

    public ServidorTCP(int porta) {
        this.PORTA = porta;
        this.clientes = new ArrayList<>();
    }

    public void executar () {
        try{
            /**
             * Criação do nosso servidor TCP
             */
            ServerSocket servidorTCP = new ServerSocket();
            servidorTCP.bind(new InetSocketAddress(PORTA));

            while (true) {
                /**
                 * Nosso código ficará travado aqui até alguém se conectar no nosso servidor
                 *
                 * Ao se conectar no nosso servidor a porta de comunicação é trocada
                 */
                Socket cliente = servidorTCP.accept();

                /**
                 * Vamos gravar na lista o canal de resposta até o cliente
                 */
                PrintStream printStream = new PrintStream(cliente.getOutputStream());
                this.clientes.add(printStream);

                /**
                 * Vamos criar a classe TrataClienteTCP
                 *
                 * O TrataCliente irá rodar numa Thread separada
                 *
                 * Teremos várias conexões. Cada conexão irá rodar em uma thread diferente
                 *
                 * Assim o servidor terá a sua thread principal para aceitar novas conexões
                 */
                TrataClienteTCP trataClienteTCP =
                        new TrataClienteTCP(cliente.getInputStream(), this);
                new Thread(trataClienteTCP).start();
            }
        } catch (SocketException e) {
            Log.d("SERVIDORTCP", "executar: " + e.getMessage());
        } catch (IOException e) {
            Log.d("SERVIDORTCP", "executar: " + e.getMessage());
        }
    }

    public void distribuiMensagem(String mensagem) {
        /**
         * Quando este método for chamado ele enviará a todos os clientes a mensagem recebida
         *
         * Vamos percorrer todos canais de comunicação e enviar a cada canal a mensagem
         */
        for (PrintStream cliente : this.clientes) {
            cliente.println(mensagem);
        }
    }

    /**
     * Inicie o servidor TCP no nosso serviço
     */

}
