package goulart.message.conversa;

/**
 * Created by Igor on 14/10/2017.
 */

public class MensagemRecebida {

    /**
     * Iremos gravar o nick do servidor e o seu conteúdo
     *
     * Vamos utilizar este objeto para carregar os servidores na lista e
     * as mensagens na lista de mensagens
     */
    private String nick;
    /**
     * O conteúdo poderá ser o ip do servidor ou a mensagem
     */
    private String conteudo;

    public MensagemRecebida(String nick, String conteudo) {
        this.nick = nick;
        this.conteudo = conteudo;
    }

    public String getNick() {
        return nick;
    }

    public String getConteudo() {
        return conteudo;
    }

}
