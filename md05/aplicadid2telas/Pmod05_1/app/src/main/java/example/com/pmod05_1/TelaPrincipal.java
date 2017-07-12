package example.com.pmod05_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaPrincipal extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        Button botao = (Button)findViewById(R.id.btnClique);
        botao.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intencao = new Intent(this, Tela2.class);
        Bundle parametros = new Bundle();
        parametros.putString("parametro1","Valor do Parâmetro 1");
        parametros.putString("parametro2","Valor do Parâmetro 2");
        intencao.putExtras(parametros);
        startActivity(intencao);

    }
}
