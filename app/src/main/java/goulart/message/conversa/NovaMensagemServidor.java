package goulart.message.conversa;

/**
 * Created by Igor on 14/10/2017.
 */

public interface NovaMensagemServidor {

    /**
     * Será preciso implementar este método
     *
     * Este método deverá receber um objeto do tipo mensagemRecebida
     */
    void novaMensagem(MensagemRecebida mensagemRecebida);

}
