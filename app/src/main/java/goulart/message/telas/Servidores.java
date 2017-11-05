package goulart.message.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import goulart.message.R;
import goulart.message.cliente.ColetarServidores;
import goulart.message.conversa.MensagemRecebida;
import goulart.message.conversa.NovaMensagemServidor;
import goulart.message.listas.gservidores.ServidoresAdapter;
import goulart.message.servico.ServicoServidor;
import goulart.message.servidores.MeuServidor;

public class Servidores extends AppCompatActivity implements NovaMensagemServidor {

    private RecyclerView listaServidoresRV;
    private ServidoresAdapter servidoresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidores);
        getSupportActionBar().setTitle("Lista de Servidores");

        // Referência ao RecyclerView
        listaServidoresRV = (RecyclerView) findViewById(R.id.listaServidores);

        // Definindo o layout do RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaServidoresRV.setLayoutManager(layoutManager);

        // Adicionar uma decoração a lista - uma linha separatória
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(listaServidoresRV.getContext(),
                layoutManager.getOrientation());
        listaServidoresRV.addItemDecoration(mDividerItemDecoration);

        // Criar o adapter que irá transformar o MeuServidor em uma lista
        // Declare o objeto fora deste método
        servidoresAdapter = new ServidoresAdapter(this);

        // Adicionar o adapter listaServidoresRV
        listaServidoresRV.setAdapter(servidoresAdapter);
        // Crie um método para inserir os objetos no adapter

        Intent intent = new Intent(this, ServicoServidor.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        apagarLista();
        carregarListaServidores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_servidores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.carregar:
                apagarLista();
                carregarListaServidores();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Chame este método no método onResume
     *
     * Chame este método quando o usuário clicar no botão recarregar a lista
     *
     * Antes lembre-se de chamar o método para limpar a lista
     *
     * Adicione a permissão:
     * <uses-permission android:name="android.permission.INTERNET" />
     */
    private void carregarListaServidores(){
        /**
         * Esta classe implementa a interface NovaMensagemServidor
         *
         * Como ela implementa o método vamos passar ela como sendo o objeto
         */
        final NovaMensagemServidor novaMensagemServidor = this;

        /**
         * Vamos criar uma thread
         *
         * Não se pode realizar nenhuma operação de acesso a rede na thread principal
         *
         * Se você realizar esta prática o seu aplicativo irá parar de funcionar
         */
        Thread coletarServidores = new Thread(){
            @Override
            public void run() {
                super.run();
                /**
                 * Estamos criando um objeto do tipo ColetarServidores
                 *
                 * Estamos passando o objeto do tipo NovaMensagemServidor
                 *
                 * Este objeto será utilizado pela classe ColetarServidores para retornar
                 * os servidores na thread principal
                 */
                ColetarServidores coletarServidores = new ColetarServidores(novaMensagemServidor);
                coletarServidores.coletarServidores();
            }
        };
        coletarServidores.start();
    }

    private void apagarLista(){
        servidoresAdapter.apagarListaServidores();
    }

    @Override
    public void novaMensagem(final MensagemRecebida mensagemRecebida) {
        /**
         * Este método será chamado a partir de outra thread
         *
         * Para manipular qualquer elemento que está disponível na tela do usuário,
         * precisamos pegar um atalho até a thread principal
         *
         * Este atalho é utilizar o runOnUiThread
         *
         * Este atalho nos fornece acesso direto a thread principal
         *
         * Agora podemos inserir um novo elemento na lista presente na tela de Servidores
         */
        runOnUiThread(new Runnable() {
            public void run() {
                MeuServidor meuServidor = new MeuServidor();
                meuServidor.setNick(mensagemRecebida.getNick());
                meuServidor.setIp(mensagemRecebida.getConteudo());
                servidoresAdapter.inserirServidor(meuServidor);
            }
        });
    }
}
