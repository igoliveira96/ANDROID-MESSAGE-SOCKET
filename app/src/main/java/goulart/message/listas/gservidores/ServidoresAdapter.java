package goulart.message.listas.gservidores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.MenuPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import goulart.message.R;
import goulart.message.servidores.MeuServidor;
import goulart.message.listas.ItemClickListener;
import goulart.message.telas.Mensagem;

/**
 * Created by Igor on 12/10/2017.
 */

public class ServidoresAdapter extends RecyclerView.Adapter<ServidoresHolder> {

    private List<MeuServidor> listaServidores;
    private Activity activity;

    /**
     * Iremos passar uma activity no construtor, neste momento não é necessário a sua utilização.
     * Iremos utilizá-la em um futuro próximo
     *
     * A nossa lista de servidores é inicializada sem nenhum valor, visto que a ideia é preencher
     * esta lista conforme é descoberto os servidores. Será um preenchimento dinâmico
     */
    public ServidoresAdapter(Activity activity){
        listaServidores = new ArrayList<>();
        this.activity = activity;
    }

    /**
     * Criação do item de visualização - ServidoresHolder
     * */
    @Override
    public ServidoresHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passagem do item de visualização para o construtor ServidoresHolder
        return new ServidoresHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lista, parent, false));
    }

    /**
     * Este método é chamado até adicionar todos os elementos da lista
     *
     * A cada chama passa-se uma posição: 0, 1, 2, ..., n
     * Método abaixo: getItemCount(); Retorna o tamanho da lista de elementos a serem mostrados
     * */
    @Override
    public void onBindViewHolder(ServidoresHolder holder, int position) {
        /**
         * A cada chamada iremos adicionar um elemento na nossa lista
         *
         * Para acessar os textviews utilizamos o nosso holder
         *
         * O holder nos dá suporte para acessar os elementos do layout_lista
         * */

        // Inserindo o nick do servidor
        holder.nick.setText(
                listaServidores.get(position).getNick()
        );

        // Inserindo a mensagem do servidor: neste caso será o IP
        holder.descricao.setText(
                listaServidores.get(position).getIp()
        );

        final int posicao = position;

        /**
         * Estamos chamando o método setItemClickListener da classe ServidoresHolder
         *
         * Estamos passando um objeto do tipo ItemClickListener
         *
         * Estamos criando este objeto dentro do parâmetnro
         */
        holder.setItemClickListener(new ItemClickListener() {
            /**
             * Método onClick sendo construído
             *
             * Estamos utilizando a posição para coletar o nick do servidor presente na lista
             */
            @Override
            public void onClick(int posicao) {
                /**
                 * Crie a seguinte variável fora deste holder:
                 * final int posicao = position;
                 *
                 * Com o Bundle podemos enviar dados para outra tela
                 */
                Intent intent = new Intent(activity, Mensagem.class);
                Bundle bundle = new Bundle();
                bundle.putString("ip", listaServidores.get(posicao).getIp());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
    }

    // Retorno da quantidade de elementos na lista
    @Override
    public int getItemCount() {
        return listaServidores.size();
    }

    /**
     * Este método será chamado sempre que o nosso servidor receber uma mensagem
     * de um servidor se identificando
     *
     * Através deste método iremos fazer com que a lista se torne dinâmica
     * */
    public void inserirServidor(MeuServidor meuServidor){
        // Adiciona na lista de servidores um novo elemento
        listaServidores.add(meuServidor);
        // Informa ao adapter que a lista foi alterada, fazendo com que o adaptador adicione
        // na lista de visualização o novo elemento
        notifyDataSetChanged();
    }

    /**
     * Este método será utilizado quando o usuário requerer a relistagem da lista
     *
     * Este método será executado quando o elemento presente no menu superior for clicado
     * */
    public void apagarListaServidores(){
        // Apagamos todos os elementos da lista
        listaServidores = new ArrayList<>();
        // Informamos ao adaptador que a lista foi alterada
        // O adaptador irá remover todos os elementos na tela
        notifyDataSetChanged();
    }

}

