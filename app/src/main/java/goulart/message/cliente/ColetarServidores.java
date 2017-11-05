package goulart.message.cliente;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

import goulart.message.conversa.MensagemRecebida;
import goulart.message.conversa.NovaMensagemServidor;
import goulart.message.rede.PlacaRede;

/**
 * Created by Igor on 14/10/2017.
 */

public class ColetarServidores {

    /**
     * Iremos receber um objeto do tipo NovaMensagemServidor
     *
     * Este objeto será utilizado para chamar-mos o método novaMensagem
     *
     * É através deste método que iremos retornar a thread principal a mensagem que recebemos
     */
    private NovaMensagemServidor novaMensagemServidor;

    /**
     * Inicializar o objeto novaMensagemServidor
     */
    public ColetarServidores(NovaMensagemServidor novaMensagemServidor){
        this.novaMensagemServidor = novaMensagemServidor;
    }


    public void coletarServidores(){
        try{
            /**
             * Vamos criar o nosso cliente
             */
            DatagramSocket clientSocket = new DatagramSocket();
            /**
             * Esta é a mesma porta que o servidor UDP utiliza
             */
            int PORTA = 9876;

            /**
             * Nossos bytes de envio e resposta
             */
            byte[] mensagemEnvio;
            byte[] resposta = new byte[1024];

            /**
             * De acordo com o nosso protocolo devemos envia a mensagem com o seguinte conteúdo:
             * identificar
             */
            String conteudo = "identificar";

            /**
             * Criação do nosso pacote de envio
             *
             * Este pacote será enviado para todos na rede
             *
             * No lugar do IP estamos adicionando o endereço de Broadcast
             */
            mensagemEnvio = conteudo.getBytes();
            DatagramPacket pacoteEnvio = new DatagramPacket(mensagemEnvio,
                    mensagemEnvio.length, PlacaRede.enderecoBroadcast(), PORTA);

            /**
             * Envio do pacote para todos na rede
             */
            clientSocket.send(pacoteEnvio);

            /**
             * Este while ficará rodando para sempre
             *
             * Este while irá ficar esperando os servidores responderem
             */
            while(true){
                /**
                 * Vamos criar um pacote que será utilizado para armazenar o pacote recebido
                 */
                DatagramPacket pacoteRecebido = new DatagramPacket(resposta, resposta.length);

                /**
                 * Nosso código irá travar nesta parte
                 *
                 * Ele ficará esperando até alguém nos enviar uma mensamge
                 */
                clientSocket.receive(pacoteRecebido);

                /**
                 * Vamos pegar a mensagem que o servidor nos enviou
                 *
                 * O trim é para remover espaços em brancos e caracteres indesejados
                 */
                String conteudoRecebido = new String(pacoteRecebido.getData()).trim();

                /**
                 * Vamos verificar se a resposta que recebemos possui a substring nick
                 *
                 * Se conter esta substring é porque recebemos um json
                 */
                if (conteudoRecebido.contains("nick")) {
                    try{
                        /**
                         * Vamos converter a string json num objeto json
                         */
                        JSONObject jsonObject = new JSONObject(conteudoRecebido);
                        /**
                         * Vamos criar um objeto do tipo MensagemRecebida
                         *
                         * Vamos coletar do json o nick do servidor e o IP
                         */
                        MensagemRecebida mensagemRecebida =
                                new MensagemRecebida(
                                        jsonObject.getString("nick"),
                                        jsonObject.getString("ip")
                                );

                        /**
                         * Vamos responder ao método novaMensagem com o objeto mensagemRecebida
                         *
                         * Este método já estará implementado
                         *
                         * Estaremos apenas o chamando e passando o que ele precisa
                         *
                         * Este método será implementado na classe Servidores
                         */
                        novaMensagemServidor.novaMensagem(mensagemRecebida);
                    } catch (JSONException e) {
                        Log.d("JSON EXCEPTION", "coletarServidores: " + e.getMessage());
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            Log.d("SOCKET EXCEPTION", "coletarServidores: " + e.getMessage());
        } catch (IOException e) {
            Log.d("IO EXCEPTION", "coletarServidores: " + e.getMessage());
        }
    }
}
