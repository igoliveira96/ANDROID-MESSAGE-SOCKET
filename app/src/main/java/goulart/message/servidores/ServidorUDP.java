package goulart.message.servidores;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import goulart.message.bancodados.GerenciaBD;
import goulart.message.rede.PlacaRede;

/**
 * Criado por Igor em 14/10/2017.
 */

public class ServidorUDP {

    private final int PORTA = 9876;
    private Context context;

    /**
     * Construtor para coletar o objeto do tipo Context
     *
     * Este objeto será útil para acessar o banco de dados
     */
    public ServidorUDP(Context context){
        this.context = context;
    }

    /**
     * Este método irá executar o nosso servidor UDP
     */
    public void executar(){
        try {

            Log.d("SERVIDOR_UDP", "executar: o servidor está executando!!!");

            /**
             * Para criar um servidor UDP criamos um objeto do tipo DatagramSocket
             *
             * Ao criar este objeto passamos a porta que o nosso servidor irá trabalhar
             *
             * É por esta porta que o nosso servidor UDP irá trabalhar
             */
            DatagramSocket servidorUDP = new DatagramSocket(PORTA);

            /**
             * Criamos um array de 1024 byte
             *
             * Estamos dizendo que a mensagem que podemos receber será de no máximo 1kb
             */
            byte[] mensagemRecebida = new byte[1024];
            byte[] resposta;

            /**
             * Este while ficará rodando para sempre
             *
             * O nosso servidor precisa responder a cada nova solicitação
             *
             * Nosso servidor não pode responder uma única vez e depois para de executar
             */
            while (true) {
                /**
                 * Estamos criando um objeto pacote
                 *
                 * Este pacote irá carregar os dados para array de bytes chamada mensagemRecebida
                 */
                DatagramPacket pacoteRecebido = new DatagramPacket(mensagemRecebida,
                        mensagemRecebida.length);

                /**
                 * O nosso código ficará travada nesta parte
                 *
                 * Ficaremos esperando até que alguém nos envie uma mensagem
                 */
                servidorUDP.receive(pacoteRecebido);

                /**
                 * Ao receber uma mensagem (pacote), iremos coletar o seu conteúdo
                 *
                 * Vamos transformar esse conteúdo em uma string
                 */
                String pacoteRecebidoConteudo = new String(pacoteRecebido.getData());

                /**
                 * Nós criamos um protocolo
                 *
                 * Iremos responder apenas se a mensagem que recebemos for igual a idenfificar
                 *
                 * Se for igual nós iremos responder dizendo o nome e o ip do servidor
                 */
                if(pacoteRecebidoConteudo.contains("identificar")){
                    /**
                     * Vamos coletar o IP do dispositivo que nos enviou a mensagem
                     *
                     * Precisamos do IP deste dispositivo para que possamos respondê-lo
                     */
                    InetAddress IPRemetente = pacoteRecebido.getAddress();

                    /**
                     * Precisamos também da porta
                     *
                     * Lembre-se que no outro dispositivo terá alguém esperando esta resposta e
                     * provavelmente estará esperando por uma porta
                     *
                     * Iremos responder pela mesma porta em que
                     * o outro dispositivo disparou a mensagem
                     */
                    int portaResposta = pacoteRecebido.getPort();

                    /**
                     * Criação do objeto json
                     *
                     * Este objeto irá gerar a string de envio
                     *
                     * O outro dispositivo irá coletar esta string e transformar em um objeto json
                     * novamente
                     */
                    JSONObject jsonObject = new JSONObject();
                    /**
                     * Estamos adicionando o nick e o ip deste servidor
                     */
                    jsonObject.put("nick", GerenciaBD.getIdentificacao(context));
                    jsonObject.put("ip", PlacaRede.meuIP(context));

                    /**
                     * Vamos carregar para o array de bytes a nossa resposta
                     *
                     * Estamos pedindo ao objeto json para gerar uma string
                     *
                     * Uma string possui o método de conversão para bytes, que é o que precisamos
                     */
                    resposta = jsonObject.toString().getBytes();

                    /**
                     * Vamos gerar o pacote de resposta
                     *
                     * Este pacote irá conter:
                     * A nossa resposta em bytes
                     * O IP para quem iremos enviar o pacote
                     * E a porta em que este pacote será enviado
                     */
                    DatagramPacket pacoteEnvio = new DatagramPacket(resposta,
                            resposta.length, IPRemetente, portaResposta);

                    /**
                     * Vamos pedir para o nosso servidor enviar o pacote
                     */
                    servidorUDP.send(pacoteEnvio);

                    /**
                     * Após executar esta última linha o código retorna ao começo do While
                     *
                     * Ao retornar ele irá novamente esperar uma nova requisição
                     */
                }
            }
        } catch (SocketException e) {
            System.out.println("ERRO SERVIDOR UDP: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERRO SERVIDOR UDP: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("ERRO SERVIDOR UDP: " + e.getMessage());
        }
    }
}

