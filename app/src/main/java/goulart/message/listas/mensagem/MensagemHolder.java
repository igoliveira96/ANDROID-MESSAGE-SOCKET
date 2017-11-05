package goulart.message.listas.mensagem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import goulart.message.R;

/**
 * Created by Igor on 15/10/2017.
 */

public class MensagemHolder extends RecyclerView.ViewHolder {

    /**
     * Iremos apenas exibir mensagens na tela
     *
     * Vamos utilizar o mesmo layout da lista de servidores
     *
     * Cada item terá um título e uma descrição (mensagem)
     *
     * O título será o nick do servidor
     * A descrição será a mensagem do servidor
     */
    TextView nick;
    TextView descricao;

    public MensagemHolder(View itemView) {
        super(itemView);
        /**
         * Coletando as referências
         */
        nick = (TextView) itemView.findViewById(R.id.identificador);
        descricao = (TextView) itemView.findViewById(R.id.descricao);
    }
}
