package goulart.message.telas;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import goulart.message.R;
import goulart.message.bancodados.GerenciaBD;
import goulart.message.cliente.Cliente;
import goulart.message.conversa.MensagemRecebida;
import goulart.message.conversa.NovaMensagemServidor;
import goulart.message.listas.mensagem.MensagemAdapter;

public class Mensagem extends AppCompatActivity implements NovaMensagemServidor {

    private RecyclerView listaMensagensRV;
    private MensagemAdapter mensagemAdapter;
    private FloatingActionButton enviarMensagemBT;
    private EditText mensagem;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem);

        listaMensagensRV = (RecyclerView) findViewById(R.id.listaMensagens);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaMensagensRV.setLayoutManager(layoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(listaMensagensRV.getContext(),
                layoutManager.getOrientation());
        listaMensagensRV.addItemDecoration(mDividerItemDecoration);

        mensagemAdapter = new MensagemAdapter();
        listaMensagensRV.setAdapter(mensagemAdapter);

        enviarMensagemBT = (FloatingActionButton) findViewById(R.id.enviarMensagemFAB);
        mensagem = (EditText) findViewById(R.id.textoEnviar);

        enviarMensagemBT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("nick", GerenciaBD.getIdentificacao(getApplicationContext()));
                    jsonObject.put("conteudo", mensagem.getText());
                    mensagem.setText("");

                    Thread threadEnviarMensagem = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            cliente.enviarMensagem(jsonObject.toString());
                        }
                    };
                    threadEnviarMensagem.start();
                } catch (JSONException e) {
                    System.out.println("ERRO DE JSON (MENSAGEM): " + e.getMessage());
                }
            }
        });

        /**
         * Para coletar os dados basta pegar a intent passada para esta tela
         *
         * Vamos coletar o extra da intent
         */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String ipServidor = bundle.getString("ip");

        cliente = new Cliente(ipServidor, 4567, this);
        conectarServidor(cliente);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cliente.encerrarConexao();
        Log.d("SERVIDOR_CONEXAO", "conexão encerrada");
    }

    private void conectarServidor(final Cliente cliente){
        Thread threadConectarCliente = new Thread(){
            @Override
            public void run() {
                super.run();
                Boolean C = cliente.conectarServidor();
                if(C){
                    Log.d("SERVIDOR_CONEXAO", "conectado");
                }
                else {
                    Log.d("SERVIDOR_CONEXAO", "desconectado");
                }
            }
        };
        threadConectarCliente.start();
    }

    /**
     * Crie o menu menu_tela_mensagens
     *
     * Adicione o item:
     * <item
     * android:id="@+id/limparMensagens"
     * android:title="Limpar mensagens" />
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_mensagens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.limparMensagens:
                mensagemAdapter.apagarMensagens();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void novaMensagem(final MensagemRecebida mensagemRecebida) {
        runOnUiThread(new Runnable() {
            public void run() {
                mensagemAdapter.inserirMensagem(mensagemRecebida);
                listaMensagensRV.scrollToPosition(mensagemAdapter.getItemCount() - 1);
            }
        });
    }

    /**
     * Altere o código de click do item da lista de servidores
     *
     * Ao cliar em qualquer servidor deverá abrir a tela de mensagens
     *
     * Aplique o arquivo: abrir_tela_mensagens
     */

    /**
        Aplique o seguinte tema:
        <style name="TemaMensagens" parent="Theme.AppCompat.Light.DarkActionBar">
            <item name="colorPrimary">@color/colorPrimary</item>
            <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
            <item name="colorAccent">@color/colorAccent</item>
        </style>

        No manifest inclua na activity Mensagem:
        android:theme="@style/TemaMensagens"

        Este tema irá corrigir a cor do EditText
     */
}
