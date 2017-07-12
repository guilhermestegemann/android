package example.com.pmod06_2;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_Cadastro extends AppCompatActivity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cadastro);

        Button btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        EditText edtNome = (EditText)findViewById(R.id.edtNome);
        EditText edtSobreNome = (EditText)findViewById(R.id.edtSobreNome);
        EditText edtTelefone = (EditText)findViewById(R.id.edtTelefone);
        EditText edtEmail = (EditText)findViewById(R.id.edtEmail);

        Intent intencao =new Intent(this, Activity_Tela2.class);
        Bundle parametros = new Bundle();
        parametros.putString("nome", (String)edtNome.getText().toString());
        parametros.putString("sobrenome", (String)edtSobreNome.getText().toString());
        parametros.putString("telefone", (String)edtTelefone.getText().toString());
        parametros.putString("email", (String)edtEmail.getText().toString());
        intencao.putExtras(parametros);
        startActivity(intencao);

    }
}
