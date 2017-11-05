package goulart.message.servidores;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Igor on 15/10/2017.
 */

/**
 * O TrataCliente ficará numa Thread Separada
 */
public class TrataClienteTCP implements Runnable {

    /**
     * Um InputStream é a mensagem que vem do cliente
     *
     * Vamos pegar a instância do ServidorTCP para mandar uma mensagem para todos os usuários
     */
    private InputStream cliente;
    private ServidorTCP servidor;

    public TrataClienteTCP(InputStream cliente, ServidorTCP servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    /**
     * Este método ficará rodando enquanto o cliente estiver conectado no servidor
     */
    public void run() {
        /**
         * Quando chegar uma mensagem o nosso código irá pedir para o servidor
         * distribuir para todos os clientes conectados
         *
         * Nosso código ficará travado no Scanner
         */
        Scanner s = new Scanner(this.cliente);
        while (s.hasNextLine()) {
            String mensagem = s.nextLine();
            servidor.distribuiMensagem(mensagem);
        }
        s.close();
    }

}
