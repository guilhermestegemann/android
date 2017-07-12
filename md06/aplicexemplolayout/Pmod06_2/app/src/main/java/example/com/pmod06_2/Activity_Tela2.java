package example.com.pmod06_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Gui on 11/03/2017.
 */

public class Activity_Tela2 extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);

        Intent intencao = getIntent();
        Bundle parametros = intencao.getExtras();

        TextView lblNomeCadastrado = (TextView)findViewById(R.id.lblNomeCadastrado);
        TextView lblSobreNomeCadastrado = (TextView)findViewById(R.id.lblSobreNomeCadastrado);
        TextView lblTelefoneCadastrado = (TextView)findViewById(R.id.lblTelefoneCadastrado);
        TextView lblEmailCadastrado = (TextView)findViewById(R.id.lblEmailCadastrado);

        lblNomeCadastrado.setText(parametros.getString("nome"));
        lblSobreNomeCadastrado.setText(parametros.getString("sobrenome"));
        lblTelefoneCadastrado.setText(parametros.getString("telefone"));
        lblEmailCadastrado.setText(parametros.getString("email"));

    }
}
