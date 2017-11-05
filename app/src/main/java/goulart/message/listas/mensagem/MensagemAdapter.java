package goulart.message.listas.mensagem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import goulart.message.R;
import goulart.message.conversa.MensagemRecebida;

/**
 * Created by Igor on 15/10/2017.
 */

public class MensagemAdapter extends RecyclerView.Adapter<MensagemHolder> {

    /**
     * Crie uma lista com o nome listaMensagens
     *
     * Esta lista irá abrigar objetos do tipo MensagemRecebida
     */
    List<MensagemRecebida> listaMensagens;

    public MensagemAdapter(){
        /**
         * Inicie a lista com um new ArrayList();
         */
        listaMensagens = new ArrayList<>();
    }

    @Override
    public MensagemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MensagemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(MensagemHolder holder, int position) {
        /**
         * Preencha o campo nick e o campo descrição
         */
        holder.nick.setText(
                listaMensagens.get(position).getNick()
        );

        holder.descricao.setText(
                listaMensagens.get(position).getConteudo()
        );
    }

    @Override
    public int getItemCount() {
        return listaMensagens != null ? listaMensagens.size() : 0;
        /*return 0;*/
    }

    /**
     * Implemente um método para inserir uma mensagem recebida na lista
     *
     * Lembre-se do tipo de objeto que a lista é composta
     *
     * Nome do método: inserirMensagem()
     */
    public void inserirMensagem(MensagemRecebida mensagemRecebida){
        listaMensagens.add(mensagemRecebida);
        notifyDataSetChanged();
    }

    /**
     * Implemente um método para apagar as mensagens da lista
     *
     * Nome do método: apagarMensagens()
     */
    public void apagarMensagens(){
        listaMensagens = new ArrayList<>();
        notifyDataSetChanged();
    }
}
