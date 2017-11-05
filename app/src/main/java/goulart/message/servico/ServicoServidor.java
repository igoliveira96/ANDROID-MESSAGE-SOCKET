package goulart.message.servico;

/**
 * Created by Igor on 13/10/2017.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import goulart.message.servidores.ServidorTCP;
import goulart.message.servidores.ServidorUDP;

/**
 * Vamos herdar as características de um service
 *
 * Ao herdar esta classe passa a ser um service (serviço)
 */
public class ServicoServidor extends Service {

    /**
     * Deixe este método de lado, faça o apenas retornar null
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Este método será chamado assim que você iniciar o serviço
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        /**
         * Vamos executar o servidor UDP em uma thread diferente
         *
         * O nosso servidor trava a execução até que alguem o envie um pacote
         *
         * Vamos então mover a sua execução para uma outra thread
         */
        Thread threadAtivarServidorUDP = new Thread(){
            @Override
            public void run() {
                super.run();
                new ServidorUDP(getApplicationContext()).executar();
            }
        };
        threadAtivarServidorUDP.start();

        Thread threadAtivarServidorTCP = new Thread(){
            @Override
            public void run() {
                super.run();
                new ServidorTCP(4567).executar();
            }
        };
        threadAtivarServidorTCP.start();

        /**
         * O Android visa economizar recurso
         *
         * Se faltar memória no dispositivo o seu serviço pode ser morto
         *
         * Para corrigir este erro, dizemos ao sistema que caso ele venha a matar o nosso serviço,
         * ele venha a religá-lo quando houver recursos suficientes para a execução
         *
         * Para o sistema religar o serviço devemos retornar a seguinte flag: START_STICKY
         */
        return START_STICKY;
    }
}
