package example.com.pmod04_2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Projmod04 extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projmod04);

        //Associando evento ao botao
        Button botao = (Button)findViewById(R.id.btAcionaButton);
        botao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText edt = (EditText)findViewById(R.id.etNomeEditText);
        TextView status = (TextView)findViewById(R.id.tvStatusTextView);
        String mensagem = "Digitou o seguinte texto: " + edt.getText().toString();
        status.setText(mensagem);
        

    }
}
