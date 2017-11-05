package goulart.message.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import goulart.message.R;
import goulart.message.bancodados.GerenciaBD;

public class Principal extends AppCompatActivity {

    /**
     * Variáveis que serão ligadas ao elementos do layout
    * */
    private EditText nickUsuario;
    private Button salvarNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Uso do find para encontrar o elemento no layout e conectar as variáveis
        nickUsuario = (EditText) findViewById(R.id.nickUsuarioET);
        salvarNick = (Button) findViewById(R.id.adicionarNickBT);

        // Setar uma ação ao botão:
        salvarNick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GerenciaBD.inserirIdentificacao(v.getContext(), nickUsuario.getText().toString());

                // Uso de um Toast para exibir uma mensagem ao usuário
                // Passagem de um contexto, uma mensagem e o tempo de duração
                // Toast.makeText(v.getContext(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                moverUsuario();
            }
        });

        moverUsuario();
    }

    private void moverUsuario(){
        if(!GerenciaBD.getIdentificacao(this).equals("ERRO")){
            //Intent: Uma definição de uma ação que será executada, como abrir uma activity
            Intent intent = new Intent(Principal.this, Servidores.class);
            startActivity(intent);
            finish();
        }
    }

}
