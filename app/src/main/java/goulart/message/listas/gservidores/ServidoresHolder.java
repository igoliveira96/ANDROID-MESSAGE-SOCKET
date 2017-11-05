package goulart.message.listas.gservidores;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import goulart.message.R;
import goulart.message.listas.ItemClickListener;

/**
 * Criado por Igor em 12/10/2017.
 */

/**
 * O View.OnClickListener é uma interface, por isso há antes dele o implements
 *
 * O método que deve ser programado é o onClick
 *
 * Não iremos implementar nesta classe a interface ItemClickListener
 *
 * Esta interface dará ao item da lista o evento de click
 */
public class ServidoresHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView nick;
    TextView descricao;

    /**
     * Criação de um objeto do tipo ItemClickListener
     *
     * Este objeto será instaciado pela classe ServidoresAdapter
     *
     * Esta classe não possui os dados que queremos manipular, somente a ServidoresAdapter
     */
    private ItemClickListener itemClickListener;

    /**
     * A classe ServidoresAdapter irá chamar este método e passar
     * um objeto do tipo ItemClickListener com o método onClick implementado
     */
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public ServidoresHolder(View itemView) {
        super(itemView);
        nick = (TextView) itemView.findViewById(R.id.identificador);
        descricao = (TextView) itemView.findViewById(R.id.descricao);

        /**
         * Esta classe está implementando o evento de click
         *
         * Por estar implementando o evento de click, este clase já então um evento de click
         *
         * Estaremos agora passando ao item da lista o evento de click, que é esta classe
         *
         * Quando o item for clicado ele chamará esta classe para resolver o problema
         */
        itemView.setOnClickListener(this);
    }

    /**
     * O método onClick será chamado quando o elemento da lista for clicado
     *
     * Estaremos chamando o método onClick da interface ItemClickListener
     *
     * Ele já foi implementado, estaremos apenas o chamando
     */
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(getAdapterPosition());
    }
}
