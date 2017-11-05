package goulart.message.servidores;

/**
 * Criado por Igor em 08/10/2017.
 */

public class MeuServidor {

    /**
     * Os atributos nick e ip serão exibidos na lista de servidores
     * */
    private String nick;
    private String ip;

    public MeuServidor(String nick, String ip){
        this.nick = nick;
        this.ip = ip;
    }

    public MeuServidor() {

    }

    public String getNick() {
        return nick;
    }

    public String getIp() {
        return ip;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}

