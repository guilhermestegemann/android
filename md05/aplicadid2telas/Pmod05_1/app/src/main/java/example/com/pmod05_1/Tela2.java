package example.com.pmod05_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Gui on 04/03/2017.
 */

public class Tela2 extends Activity implements OnClickListener{

    @Override
     protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_2);

        Button botao = (Button)findViewById(R.id.btnTelaAnterior);
        botao.setOnClickListener(this);

        Intent intencao = getIntent();
        Bundle parametros = intencao.getExtras();
        String parametro1 = parametros.getString("parametro1");
        String parametro2 = parametros.getString("parametro2");

        TextView status = (TextView)findViewById(R.id.txtStatus);
        status.setText("Chegaram os parametros "+ parametro1 + " e " + parametro2);
    }

    @Override
    public void onClick(View v) {
        finish();

    }
}
